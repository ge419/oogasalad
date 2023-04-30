package oogasalad.model.exception;

public class SchemaException extends RuntimeException {

  public SchemaException(String message) {
    super(message);
  }

  public SchemaException(Throwable cause, String message) {
    super(message, cause);
  }
}
