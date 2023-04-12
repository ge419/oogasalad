package oogasalad.view.builder.exceptions;

/**
 * InvalidSizeException will occur whenever a thing is an invalid/illegal size.
 *
 * @author tmh85
 */
public class InvalidSizeException extends RuntimeException {

  public InvalidSizeException(String errorMessage, Throwable originalError) {
    super(errorMessage, originalError);
  }

  public InvalidSizeException(String errorMessage) {
    super(errorMessage);
  }
}
