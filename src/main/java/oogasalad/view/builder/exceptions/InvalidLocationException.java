package oogasalad.view.builder.exceptions;

public class InvalidLocationException extends RuntimeException{
  public InvalidLocationException(String errorMessage, Throwable originalError){
    super(errorMessage, originalError);
  }
  public InvalidLocationException(String errorMessage){
    super(errorMessage);
  }
}
