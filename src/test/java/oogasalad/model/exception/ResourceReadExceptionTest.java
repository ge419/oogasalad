package oogasalad.model.exception;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceReadExceptionTest {

  public static final String TEST_MESSAGE = "Test message";
  public static final String TEST_CAUSE = "Test cause";

  @Test
  public void validErrorMessage() {
    ResourceReadException exception = new ResourceReadException(TEST_MESSAGE);
    assertEquals(TEST_MESSAGE, exception.getMessage());
  }

  @Test
  public void validErrorCause() {
    Exception cause = new Exception(TEST_CAUSE);
    ResourceReadException exception = new ResourceReadException(cause);
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void validMessageWithCause() {
    Exception cause = new Exception(TEST_CAUSE);
    ResourceReadException exception = new ResourceReadException(TEST_MESSAGE, cause);
    assertEquals(TEST_MESSAGE, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void suppressionAndStackTrace() {
    Exception cause = new Exception(TEST_CAUSE);
    boolean enableSuppression = false;
    boolean writableStackTrace = false;
    ResourceReadException exception = new ResourceReadException(TEST_MESSAGE, cause, enableSuppression, writableStackTrace);
    assertEquals(TEST_MESSAGE, exception.getMessage());
    assertEquals(cause, exception.getCause());
    assertFalse(exception.getSuppressed().length > 0);
    assertFalse(exception.getStackTrace().length > 0);
  }

}
