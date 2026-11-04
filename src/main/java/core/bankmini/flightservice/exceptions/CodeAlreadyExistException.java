package core.bankmini.flightservice.exceptions;

public class CodeAlreadyExistException extends RuntimeException {
    public CodeAlreadyExistException(String code) {
        super( "This airline whies: " + code + " already exists");
    }
}
