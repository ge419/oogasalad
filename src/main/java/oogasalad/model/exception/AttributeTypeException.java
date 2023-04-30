package oogasalad.model.exception;

public class AttributeTypeException extends RuntimeException {

  public AttributeTypeException(String message) {
    super(message);
  }

  public AttributeTypeException(Throwable cause, String message) {
    super(message, cause);
  }
}
