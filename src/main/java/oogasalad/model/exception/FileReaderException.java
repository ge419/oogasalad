package oogasalad.model.exception;

/**
 * Exception class that handles errors in reading file
 *
 * @author Nathaniel Wullar
 */
public class FileReaderException extends Exception {

  /**
   * Constructor for the FileReaderException class
   *
   * @param error the error
   */
  public FileReaderException(String error) {
    super(error);
  }

}
