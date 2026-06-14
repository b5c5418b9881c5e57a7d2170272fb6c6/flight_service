package core.bankmini.flightservice.util;

import core.bankmini.flightservice.model.entity.Airline;

import java.util.Random;
import java.util.UUID;

public class AirlineDataUtil {

    public static Airline getAirlineObject(String iataCode, String icaoCode) {
        return Airline.builder()
                .id(UUID.randomUUID())
                .name("Airline name " + new Random().nextInt(100))
                .iataCode(iataCode)
                .icaoCode(icaoCode)
                .country("Some were")
                .build();
    }

}
