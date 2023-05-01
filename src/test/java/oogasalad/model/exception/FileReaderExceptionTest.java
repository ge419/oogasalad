package oogasalad.model.exception;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileReaderExceptionTest {

  public static final String TEST_MESSAGE = "An error occurred while reading the file.";

  @Test
  public void validErrorMessage() {
    FileReaderException exception = new FileReaderException(TEST_MESSAGE);
    assertEquals(TEST_MESSAGE, exception.getMessage());
  }

}
