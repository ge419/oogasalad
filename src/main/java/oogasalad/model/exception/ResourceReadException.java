package oogasalad.model.exception;

public class ResourceReadException extends RuntimeException {

  public ResourceReadException(String message) {
    super(message);
  }

  public ResourceReadException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceReadException(Throwable cause) {
    super(cause);
  }

  public ResourceReadException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
