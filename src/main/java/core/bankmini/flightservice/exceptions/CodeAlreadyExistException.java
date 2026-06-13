package core.bankmini.flightservice.exceptions;

public class CodeAlreadyExistException extends RuntimeException {
    public CodeAlreadyExistException(String code, String codeName) {
        super(codeName + ": " + code + " already exists");
    }
}
