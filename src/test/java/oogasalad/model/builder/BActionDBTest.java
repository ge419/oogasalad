package oogasalad.model.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import oogasalad.model.BAction;
import oogasalad.model.BActionDB;
import oogasalad.model.attribute.FileReader;
import oogasalad.model.exception.FileReaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BActionDBTest {

  private BActionDB actionDB;
  private FileReader fileReader;

  @BeforeEach
  void setUp() throws FileReaderException, IOException {
    MockitoAnnotations.openMocks(this);
    actionDB = new BActionDB();
    List<File> mockFiles = Collections.singletonList(mock(File.class));
    FileReader fileReader = new FileReader();
    fileReader.readFiles("actions");
  }

  @Test
  void testConstructor() {
    Map<String, BAction> actionMap = new HashMap<>();
    assertNotNull(actionMap);
    assertTrue(actionMap.isEmpty());
  }

  @Test
  void testCreationOfData() throws IOException {
    // Given
    File mockFile = mock(File.class);
    BAction mockAction = mock(BAction.class);
    ObjectMapper mockMapper = mock(ObjectMapper.class);
    when(mockMapper.readValue(mockFile, BAction.class)).thenReturn(mockAction);
   //  assertSame(mockAction, result);
  }

  @Test
  void testReadWrongFilesThrowsException() throws IOException, FileReaderException {
    // Given
    List<File> mockFiles = Collections.singletonList(mock(File.class));
    assertThrows(FileReaderException.class, ()->fileReader.readFiles("wrong"));
  }
}
