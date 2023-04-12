package oogasalad.view.builder.exceptions;

/**
 * InvalidLocationException is an exception that will occur whenever a thing is in an invalid
 * location.
 *
 * @author tmh85
 */
public class InvalidLocationException extends RuntimeException {

  public InvalidLocationException(String errorMessage, Throwable originalError) {
    super(errorMessage, originalError);
  }

  public InvalidLocationException(String errorMessage) {
    super(errorMessage);
  }
}
