package gameplay_frontend;

public class InvalidMoveException extends RuntimeException {
  public InvalidMoveException(String message) {
    super(message);
  }
}