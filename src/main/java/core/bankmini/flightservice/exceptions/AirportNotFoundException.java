package core.bankmini.flightservice.exceptions;

import java.util.UUID;

public class AirportNotFoundException extends RuntimeException {
    public AirportNotFoundException(UUID id) {
        super("Airport with id " + id + " not found");
    }
}
