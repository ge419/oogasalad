package oogasalad.model.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileReaderExceptionTest {

  public static final String TEST_MESSAGE = "An error occurred while reading the file.";

  @Test
  public void validErrorMessage() {
    FileReaderException exception = new FileReaderException(TEST_MESSAGE);
    assertEquals(TEST_MESSAGE, exception.getMessage());
  }

}
