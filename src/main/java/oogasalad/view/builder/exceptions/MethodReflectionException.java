package oogasalad.view.builder.exceptions;

/**
 * <p>Exception thrown if reflective method get fails.</p>
 *
 * @author tmh85
 */
public class MethodReflectionException extends RuntimeException {

  public MethodReflectionException(String errorMessage, Throwable originalError) {
    super(errorMessage, originalError);
  }

  public MethodReflectionException(String errorMessage) {
    super(errorMessage);
  }
}
