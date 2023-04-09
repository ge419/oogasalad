package oogasalad.view.builder.exceptions;

public class InvalidSizeException extends RuntimeException{
  public InvalidSizeException(String errorMessage, Throwable originalError){
    super(errorMessage, originalError);
  }

  public InvalidSizeException(String errorMessage){
    super(errorMessage);
  }
}
