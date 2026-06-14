package core.bankmini.flightservice.util;

import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.model.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class FlightDataUtil {

    public static Flight getFlightPersisted(
            Airline airline, Aircraft aircraft,
            Airport departure, Airport arrival,
            String flightNumber
    ) {
        return Flight.builder()
                .id(UUID.randomUUID())
                .flightNumber(flightNumber)
                .airline(airline)
                .aircraft(aircraft)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureAt(LocalDateTime.now().plusDays(1))
                .arrivalAt(LocalDateTime.now())
                .seatsTotal(new Random().nextInt(1000))
                .terminal("M" + new Random().nextInt(220))
                .gate("A" + new Random().nextInt(1000))
                .build();
    }

    public static Flight getFlightPersisted(
            Airline airline, Aircraft aircraft,
            Airport departure, Airport arrival,
            String flightNumber, LocalDateTime departureAt
    ) {
        return Flight.builder()
                .id(UUID.randomUUID())
                .flightNumber(flightNumber)
                .airline(airline)
                .aircraft(aircraft)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureAt(departureAt)
                .arrivalAt(LocalDateTime.now())
                .seatsTotal(new Random().nextInt(1000))
                .terminal("M" + new Random().nextInt(220))
                .gate("A" + new Random().nextInt(1000))
                .status(FlightStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
    }

}
