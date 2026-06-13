package core.bankmini.flightservice.exceptions;

import java.util.UUID;

public class AircraftNotFoundException extends RuntimeException {
    public AircraftNotFoundException(UUID id) {
        super("Aircraft with id " + id + " not found");
    }
}
