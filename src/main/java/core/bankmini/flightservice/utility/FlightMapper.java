package core.bankmini.flightservice.utility;

import core.bankmini.flightservice.model.dto.flight.CreateFlightDtoRequest;
import core.bankmini.flightservice.model.dto.flight.FlightSearchDto;
import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.model.enums.FlightStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FlightMapper {

    public Flight mapCreateDtoNewFlightToEntity(CreateFlightDtoRequest dtoFlight,
                                                Airline airline,
                                                Aircraft aircraft,
                                                Airport departureAirport,
                                                Airport arrivalAirport) {
        final Duration duration = Duration.between(dtoFlight.departureAt(), dtoFlight.arrivalAt());

        return Flight.builder()
                .id(UUID.randomUUID())
                .flightNumber(dtoFlight.flightNumber())
                .airline(airline)
                .aircraft(aircraft)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureAt(dtoFlight.departureAt().toLocalDateTime())
                .arrivalAt(dtoFlight.arrivalAt().toLocalDateTime())
                .durationMinutes(Math.toIntExact(duration.toMinutes()))
                .seatsTotal(aircraft.getSeatsTotal())
                .terminal(dtoFlight.terminal())
                .gate(dtoFlight.gate())
                .status(FlightStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
    }

    public FlightSearchDto mapFlightToSearchDto(
            Flight flight,
            @Valid String timezoneDeparture, // "Asia/Almaty"
            @Valid String timezoneArrival    // "Asia/Almaty"
    ) {

        return FlightSearchDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airlineId(flight.getAirline().getId())
                .aircraftId(flight.getAircraft().getId())
                .departureAirportId(flight.getDepartureAirport().getId())
                .arrivalAirportId(flight.getArrivalAirport().getId())
                .departureAt(flight.getDepartureAt().atZone(ZoneId.of(timezoneDeparture)))
                .arrivalAt(flight.getArrivalAt().atZone(ZoneId.of(timezoneArrival)))
                .durationMinutes(flight.getDurationMinutes())
                .seatsTotal(flight.getSeatsTotal())
                .terminal(flight.getTerminal())
                .gate(flight.getGate())
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public List<FlightSearchDto> mapFlightsToSearchDto(List<Flight> flights) {
        return flights.stream()
                .map(this::mapFlightToSearchDto)
                .toList();
    }

    private FlightSearchDto mapFlightToSearchDto(Flight flight) {
        return FlightSearchDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airlineId(flight.getAirline().getId())
                .aircraftId(flight.getAircraft().getId())
                .departureAirportId(flight.getDepartureAirport().getId())
                .arrivalAirportId(flight.getArrivalAirport().getId())
                .departureAt(flight.getDepartureAt().atZone(ZoneId.of(flight.getDepartureAirport().getTimezone())))
                .arrivalAt(flight.getArrivalAt().atZone(ZoneId.of(flight.getArrivalAirport().getTimezone())))
                .durationMinutes(flight.getDurationMinutes())
                .seatsTotal(flight.getSeatsTotal())
                .terminal(flight.getTerminal())
                .gate(flight.getGate())
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

}
