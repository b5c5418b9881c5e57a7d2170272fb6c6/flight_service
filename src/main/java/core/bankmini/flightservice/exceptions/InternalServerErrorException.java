package core.bankmini.flightservice.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message, Exception e) {
        super(message, e);
    }
}
