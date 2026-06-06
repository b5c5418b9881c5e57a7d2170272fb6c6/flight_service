package core.bankmini.flightservice.exceptions;

import java.util.UUID;

public class AirlineNotFoundException extends RuntimeException {
  public AirlineNotFoundException(UUID id) {
    super("Airline with id " + id + " not found");
  }
}
