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

  @Mock
  private FileReader mockFileReader;

  @BeforeEach
  void setUp() throws FileReaderException, IOException {
    MockitoAnnotations.openMocks(this);
    actionDB = new BActionDB();
    List<File> mockFiles = Collections.singletonList(mock(File.class));
    when(mockFileReader.readFiles("actions")).thenReturn(mockFiles);
  }

  @Test
  void testConstructor() {
    Map<String, BAction> actionMap = new HashMap<>();
    assertNotNull(actionMap);
    assertTrue(actionMap.isEmpty());
  }
  //TODO: uncomment and finish the tests
//  @Test
//  void testCreationOfData() throws IOException {
//    // Given
//    File mockFile = mock(File.class);
//    BAction mockAction = mock(BAction.class);
//    ObjectMapper mockMapper = mock(ObjectMapper.class);
//    when(mockMapper.readValue(mockFile, BAction.class)).thenReturn(mockAction);
//
//    // When
//    BAction result = actionDB.createData(mockFile, mockMapper);
//
//    // Then
//    assertSame(mockAction, result);
//  }
//
//  @Test
//  void testReadFiles() throws IOException, FileReaderException {
//    // Given
//    List<File> mockFiles = Collections.singletonList(mock(File.class));
//    BAction mockAction = mock(BAction.class);
//    when(mockFileReader.readFiles("actions")).thenReturn(mockFiles);
//    when(actionDB.createData(any(File.class), any(ObjectMapper.class))).thenReturn(mockAction);
//
//    // When
//    actionDB.readFiles();
//
//    // Then
//    Map<String, BAction> actionMap = actionDB.getActionMap();
//    assertNotNull(actionMap);
//    assertEquals(1, actionMap.size());
//    assertSame(mockAction, actionMap.get("name"));
//  }
//
//  @Test
//  void testReadFiles_fileReaderThrowsException() throws IOException, FileReaderException {
//    // Given
//    when(mockFileReader.readFiles("actions")).thenThrow(new FileReaderException("test"));
//
//    // When / Then
//    assertThrows(RuntimeException.class, () -> actionDB.readFiles());
//  }
//
//  @Test
//  void testReadFiles_createDataThrowsException() throws IOException, FileReaderException {
//    // Given
//    List<File> mockFiles = Collections.singletonList(mock(File.class));
//    when(mockFileReader.readFiles("actions")).thenReturn(mockFiles);
//    when(actionDB.createData(any(File.class), any(ObjectMapper.class))).thenThrow(new IOException("test"));
//
//    // When / Then
//    assertThrows(RuntimeException.class, () -> actionDB.readFiles());
//  }
}
