package oogasalad.util;

public class SaveManagerException extends RuntimeException {

  public SaveManagerException(String message) {
    super(message);
  }

  public SaveManagerException(String message, Throwable cause) {
    super(message, cause);
  }
}
