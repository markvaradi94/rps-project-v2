package ro.fasttrack.rpsproject.exceptions;

public class SamePlayerException extends RuntimeException {
    public SamePlayerException(String message) {
        super(message);
    }
}
