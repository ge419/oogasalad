package oogasalad.model.exception;

public class BTypeException extends RuntimeException {

  public BTypeException(String message) {
    super(message);
  }

  public BTypeException(Throwable cause, String message) {
    super(message, cause);
  }
}
