package core.bankmini.flightservice.utility;

import core.bankmini.flightservice.model.dto.flight.CreateFlightDtoRequest;
import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.model.enums.FlightStatus;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.UUID;

@Component
public class FlightMapper {

    public ZonedDateTime convertLocalDateTimeToZonedDateTime(String timeZone) {
        return ZonedDateTime.now(ZoneId.of(timeZone));
    }

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


}
