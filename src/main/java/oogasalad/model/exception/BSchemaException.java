package oogasalad.model.exception;

public class BSchemaException extends RuntimeException {

  public BSchemaException(String message) {
    super(message);
  }

  public BSchemaException(Throwable cause, String message) {
    super(message, cause);
  }
}
