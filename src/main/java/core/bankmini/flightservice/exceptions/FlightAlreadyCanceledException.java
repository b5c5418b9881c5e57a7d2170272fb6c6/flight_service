package core.bankmini.flightservice.exceptions;

public class FlightAlreadyCanceledException extends RuntimeException {
    public FlightAlreadyCanceledException(String message) {
        super(message);
    }
}
