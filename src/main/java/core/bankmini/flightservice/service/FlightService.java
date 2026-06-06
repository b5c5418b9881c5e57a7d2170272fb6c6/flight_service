package core.bankmini.flightservice.service;

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
import core.bankmini.flightservice.utility.FlightMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;

    private Executor executor;
    private final FlightMapper mapper;

    @Transactional
    public UUID createFlight(CreateFlightDtoRequest dtoRequest) {

        //todo: add filters check of null or empty fields on request

        CompletableFuture<Airline> airlineFuture = CompletableFuture.supplyAsync(
                () -> airlineRepository.findById(dtoRequest.airlineId())
                        .orElseThrow(() -> new RuntimeException("Airline with id " + dtoRequest.airlineId() + " not found")),
                executor);

        CompletableFuture<Aircraft> aircraftFuture = CompletableFuture.supplyAsync(
                () -> aircraftRepository.findById(dtoRequest.aircraftId())
                        .orElseThrow(() -> new IllegalArgumentException("Aircraft with id " + dtoRequest.aircraftId() + " not found")),
                executor);

        CompletableFuture<Airport> departureAirportFuture = CompletableFuture.supplyAsync(
                () -> airportRepository.findById(dtoRequest.departureAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Airport with id " + dtoRequest.departureAirport() + " not found")),
                executor);
        CompletableFuture<Airport> arrivalAirportFuture = CompletableFuture.supplyAsync(
                () -> airportRepository.findById(dtoRequest.arrivalAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Airport with id " + dtoRequest.departureAirport() + " not found")),
                executor);
        CompletableFuture.allOf(airlineFuture, aircraftFuture, departureAirportFuture, arrivalAirportFuture).join();

        Airline airline = airlineFuture.join();
        Aircraft aircraft = aircraftFuture.join();
        Airport departureAirport = departureAirportFuture.join();
        Airport arrivalAirport = arrivalAirportFuture.join();

        try {
            Flight savedFlight = flightRepository.save(
                    mapper.mapCreateDtoNewFlightToEntity(
                            dtoRequest,
                            airline,
                            aircraft,
                            departureAirport,
                            arrivalAirport
                    )
            );
            log.info("Successfully created flight: {}", savedFlight.getId());
            return savedFlight.getId();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while saving flight", e);
        }
    }

    public FlightSearchDto getFlightById(UUID flightId) {
        var flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight with id " + flightId + " not found"));

        // todo: оптимизировать получение данных по аэропорту

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

        fl.setStatus(FlightStatus.valueOf(status));
    }

    @Transactional
    public void cancelFlight(@Valid UUID id) {
        var fl = flightRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight with id " + id + " not found"));
        fl.setStatus(FlightStatus.CANCELLED);
    }

    public List<FlightSearchDto> searchAllFlights(
            String from, String to,
            @Nullable LocalDate departureDate,
            @Nullable LocalDate arrivalDate) {

        if (from == null || to == null) {
            throw new IllegalArgumentException("Validation failed from or to airport");
        }

        LocalDateTime startDayDeparture;
        LocalDateTime endDayDeparture;

        LocalDateTime startDayArrival;
        LocalDateTime endDayArrival;

        List<Flight> listFlights = List.of();

        if (departureDate != null && arrivalDate == null) {

            startDayDeparture = departureDate.atStartOfDay();
            endDayDeparture = departureDate.plusDays(1).atStartOfDay();

            listFlights = flightRepository.searchAllFlightsWithDeparture(from, to, startDayDeparture, endDayDeparture);
        }
        if (departureDate == null && arrivalDate != null) {

            startDayArrival = arrivalDate.atStartOfDay();
            endDayArrival = arrivalDate.plusDays(1).atStartOfDay();

            listFlights = flightRepository.searchAllFlightsWithArrival(from, to, startDayArrival, endDayArrival);
        }
        if (departureDate != null && arrivalDate != null) {

            startDayDeparture = departureDate.atStartOfDay();
            endDayDeparture = departureDate.plusDays(1).atStartOfDay();
            startDayArrival = arrivalDate.atStartOfDay();
            endDayArrival = arrivalDate.plusDays(1).atStartOfDay();

            listFlights = flightRepository.searchAllFlightsWithDate(
                    from, to,
                    startDayDeparture, endDayDeparture,
                    startDayArrival, endDayArrival
            );
        }
        if (departureDate == null && arrivalDate == null) {
            listFlights = flightRepository.searchAllFlightsWithoutDate(from, to);
        }

        return mapper.mapFlightsToSearchDto(listFlights);
    }

    @Transactional(readOnly = true)
    public FlightSearchDto getFlightByNumber(String flightNumber) {
        var flight = flightRepository.findByFlightNumber(flightNumber);
        return mapper.mapFlightToSearchDto(
                flight,
                flight.getDepartureAirport().getTimezone(),
                flight.getArrivalAirport().getTimezone()
        );
    }


}
