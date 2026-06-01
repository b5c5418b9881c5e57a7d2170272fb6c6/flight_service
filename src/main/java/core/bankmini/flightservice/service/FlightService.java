package core.bankmini.flightservice.service;

import core.bankmini.flightservice.model.dto.flight.CreateFlightDtoRequest;
import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.repository.AircraftRepository;
import core.bankmini.flightservice.repository.AirlineRepository;
import core.bankmini.flightservice.repository.AirportRepository;
import core.bankmini.flightservice.repository.FlightRepository;
import core.bankmini.flightservice.utility.FlightMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        .orElseThrow(() -> new RuntimeException("Aircraft with id " + dtoRequest.aircraftId() + " not found")),
                executor);

        CompletableFuture<Airport> departureAirportFuture = CompletableFuture.supplyAsync(
                () -> airportRepository.findById(dtoRequest.departureAirport())
                        .orElseThrow(() -> new RuntimeException("Airport with id " + dtoRequest.departureAirport() + " not found")),
                executor);
        CompletableFuture<Airport> arrivalAirportFuture = CompletableFuture.supplyAsync(
                () -> airportRepository.findById(dtoRequest.arrivalAirport())
                        .orElseThrow(() -> new RuntimeException("Airport with id " + dtoRequest.departureAirport() + " not found")),
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


}
