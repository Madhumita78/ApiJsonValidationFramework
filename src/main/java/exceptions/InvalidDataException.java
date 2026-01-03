package exceptions;

public class InvalidDataException extends ApiException {
    public InvalidDataException(String message) {
        super(message);
    }
}
