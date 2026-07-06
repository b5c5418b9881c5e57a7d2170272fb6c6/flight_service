package core.bankmini.flightservice.service;

import core.bankmini.flightservice.exceptions.AircraftNotFoundException;
import core.bankmini.flightservice.exceptions.AirlineNotFoundException;
import core.bankmini.flightservice.exceptions.AirportNotFoundException;
import core.bankmini.flightservice.model.dto.KafkaTopics;
import core.bankmini.flightservice.model.dto.events.FlightCancelledEvent;
import core.bankmini.flightservice.model.dto.events.FlightCreateEvent;
import core.bankmini.flightservice.model.dto.flight.CreateFlightDtoRequest;
import core.bankmini.flightservice.model.dto.flight.FlightSearchDto;
import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.model.enums.FlightStatus;
import core.bankmini.flightservice.repository.AircraftRepository;
import core.bankmini.flightservice.repository.AirlineRepository;
import core.bankmini.flightservice.repository.AirportRepository;
import core.bankmini.flightservice.repository.FlightRepository;
import core.bankmini.flightservice.utility.FlightEventConverter;
import core.bankmini.flightservice.utility.FlightMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    // todo: add update flight logic

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper mapper;
    private final FightEventProducer producer;
    private final FlightEventConverter converter;

    @Transactional
    public UUID createFlight(CreateFlightDtoRequest dtoRequest) {

        Boolean existFlight = flightRepository.existsByFlightNumberIs(dtoRequest.flightNumber());

        if (existFlight != false) {
            throw new IllegalArgumentException("Flight with this number %s already exist".formatted(dtoRequest.flightNumber()));
        }

        Airline airline = airlineRepository.findById(dtoRequest.airlineId())
                .orElseThrow(() ->
                        new AirlineNotFoundException(dtoRequest.airlineId()));

        Aircraft aircraft = aircraftRepository.findById(dtoRequest.aircraftId())
                .orElseThrow(() ->
                        new AircraftNotFoundException(dtoRequest.aircraftId()));

        Airport departureAirport = airportRepository.findById(dtoRequest.departureAirport())
                .orElseThrow(() ->
                        new AirportNotFoundException(dtoRequest.departureAirport()));

        Airport arrivalAirport = airportRepository.findById(dtoRequest.arrivalAirport())
                .orElseThrow(() ->
                        new AirportNotFoundException(dtoRequest.arrivalAirport()));

        Flight flight = mapper.mapCreateDtoNewFlightToEntity(
                dtoRequest,
                airline,
                aircraft,
                departureAirport,
                arrivalAirport
        );

        Flight savedFlight = flightRepository.save(flight);

        log.info("FlightService: Successfully created flight: {}", savedFlight.getId());

        FlightCreateEvent value = converter.convertCreateEvent(savedFlight);

        producer.send(
                KafkaTopics.FLIGHT_EVENTS,
                savedFlight.getFlightNumber(),
                value
        );

        log.info("FlightService: Successfully send from topic: {}", KafkaTopics.FLIGHT_EVENTS);

        return savedFlight.getId();
    }

    public FlightSearchDto getFlightById(UUID flightId) {
        var flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight with id " + flightId + " not found"));

        // todo: оптимизировать получение данных по аэропорту

        log.info("FlightService: Getting  flight: {}", flight.getId());

        return mapper.mapFlightToSearchDto(
                flight,
                flight.getDepartureAirport().getTimezone(),
                flight.getArrivalAirport().getTimezone()
        );
    }

    @Transactional
    public void changeFlightStatus(UUID id, String status) {
        if (!FlightStatus.contains(status)) {
            throw new IllegalArgumentException(String.format("Status %s not found", status));
        }

        var fl = flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight with id " + id + " not found"));
        log.info("FlightService: Changing flight id: {}", fl.getId());
        fl.setStatus(FlightStatus.valueOf(status));
    }

    @Transactional
    public void cancelFlight(@NotNull UUID id) {
        var fl = flightRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight with id " + id + " not found"));
        fl.setStatus(FlightStatus.CANCELLED);
        log.info("FlightService: Cancelling flight: {}", fl.getId());

        FlightCancelledEvent value = converter.convertCancelEvent(fl);

        producer.send(
                KafkaTopics.FLIGHT_EVENTS,
                fl.getFlightNumber(),
                value
        );

        log.info("FlightService: Successfully send from topic: {}", KafkaTopics.FLIGHT_EVENTS);

    }

    public List<FlightSearchDto> searchAllFlights(
            String from,
            String to,
            LocalDate departureDate,
            LocalDate arrivalDate,
            int page,
            int size) {

        if (from == null || from.isBlank()
                || to == null || to.isBlank()) {
            throw new IllegalArgumentException("From and to airports must be specified");
        }

        List<Flight> flights;
        Pageable pageable = PageRequest.of(page, size);

        if (departureDate != null && arrivalDate == null) {

            flights = flightRepository.searchAllFlightsWithDeparture(
                    from,
                    to,
                    departureDate.atStartOfDay(),
                    departureDate.plusDays(1).atStartOfDay(),
                    pageable
            );

        } else if (departureDate == null && arrivalDate != null) {

            flights = flightRepository.searchAllFlightsWithArrival(
                    from,
                    to,
                    arrivalDate.atStartOfDay(),
                    arrivalDate.plusDays(1).atStartOfDay(),
                    pageable
            );

        } else if (departureDate != null) {

            flights = flightRepository.searchAllFlightsWithDate(
                    from,
                    to,
                    departureDate.atStartOfDay(),
                    departureDate.plusDays(1).atStartOfDay(),
                    arrivalDate.atStartOfDay(),
                    arrivalDate.plusDays(1).atStartOfDay(),
                    pageable
            );

        } else {

            flights = flightRepository.searchAllFlightsWithoutDate(from, to, pageable);
        }

        log.info(
                "Found {} flights: from={}, to={}, departureDate={}, arrivalDate={}",
                flights.size(),
                from,
                to,
                departureDate,
                arrivalDate
        );

        return mapper.mapFlightsToSearchDto(flights);
    }

    @Transactional(readOnly = true)
    public FlightSearchDto getFlightByNumber(String flightNumber) {
        var flight = flightRepository.findByFlightNumber(flightNumber);
        log.info("FlightService: Getting flight by number: {}", flight.getFlightNumber());
        return mapper.mapFlightToSearchDto(
                flight,
                flight.getDepartureAirport().getTimezone(),
                flight.getArrivalAirport().getTimezone()
        );
    }

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void cancelFlights() {
        flightRepository.cancelExpiredFlights(LocalDateTime.now());
    }


}
