package oogasalad.model.exception;

public class InvalidDatabaseExecutionException extends RuntimeException{

  public InvalidDatabaseExecutionException(String message) {
    super(message);
  }

  public InvalidDatabaseExecutionException(String message, Throwable cause) {
    super(message, cause);
  }

}
