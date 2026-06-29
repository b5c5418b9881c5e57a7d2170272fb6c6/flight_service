package core.bankmini.flightservice.util;

import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.model.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlightDataUtil {

    public static Flight getFlightPersisted(
            Airline airline,
            Aircraft aircraft,
            Airport departure,
            Airport arrival,
            String flightNumber
    ) {
        LocalDateTime base = LocalDateTime.now();
        LocalDateTime departureAt = base.plusDays(1);
        LocalDateTime arrivalAt = departureAt.plusHours(2);

        return Flight.builder()
                .id(UUID.randomUUID())
                .flightNumber(flightNumber)
                .airline(airline)
                .aircraft(aircraft)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureAt(departureAt)
                .arrivalAt(arrivalAt)
                .durationMinutes(120)
                .seatsTotal(180)
                .terminal("T1")
                .gate("A1")
                .status(FlightStatus.CREATED)
                .build();
    }

    public static Flight getFlightPersisted(
            Airline airline,
            Aircraft aircraft,
            Airport departure,
            Airport arrival,
            String flightNumber,
            LocalDateTime departureAt
    ) {
        LocalDateTime arrivalAt = departureAt.plusHours(2);

        return Flight.builder()
                .id(UUID.randomUUID())
                .flightNumber(flightNumber)
                .airline(airline)
                .aircraft(aircraft)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureAt(departureAt)
                .arrivalAt(arrivalAt)
                .durationMinutes(120)
                .seatsTotal(180)
                .terminal("T1")
                .gate("A1")
                .status(FlightStatus.CREATED)
                .build();
    }
}