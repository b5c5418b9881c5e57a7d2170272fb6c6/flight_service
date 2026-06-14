package core.bankmini.flightservice.util;

import core.bankmini.flightservice.model.entity.Aircraft;

import java.util.Random;
import java.util.UUID;

public class AircraftDataUtil {

    public static Aircraft getAircraftObject() {
        return Aircraft.builder()
                .id(UUID.randomUUID())
                .model("Boing " + new Random().nextInt(1000) + " series")
                .manufactured("sho?")
                .seatsTotal(new Random().nextInt(50, 300))
                .build();
    }

}
