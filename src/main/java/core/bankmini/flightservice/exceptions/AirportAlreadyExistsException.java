package core.bankmini.flightservice.exceptions;

public class AirportAlreadyExistsException extends RuntimeException {

    public AirportAlreadyExistsException(String airportName) {
        super("Airport already exists: " + airportName);
    }
}