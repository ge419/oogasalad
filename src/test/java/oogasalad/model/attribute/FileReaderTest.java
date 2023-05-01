package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.IOException;
import java.util.List;
import oogasalad.model.exception.FileReaderException;
import org.junit.jupiter.api.Test;

class FileReaderTest {

  @Test
  void testReading() throws FileReaderException, IOException {
    List<File> file = FileReader.readFiles("schemas");
    assertFalse(file.isEmpty());
  }

  @Test
  void testThrowsExceptionGivenWrongFilePath() {
    assertThrows(NullPointerException.class,
        () -> FileReader.readFiles("some invalid file path"));
  }

}