package oogasalad.view.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.ResourceBundle;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.board.BoardImage;
import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BoardInfoTest extends DukeApplicationTest {

  private static final String BASE_RESOURCE_PACKAGE = "main.resources.view.builder.";
  private BoardInfo myInfo;

//  @Mock
//  ResourceBundle myResource;

  @BeforeEach
  void setUp() {
    ResourceBundle myResource = mock(ResourceBundle.class);
    assertNotNull(myResource);
    when(myResource.getString(any())).thenReturn("we done errored correctly");
    myInfo = new BoardInfo(myResource);
  }

  @Test
  void testImageCreation() {
    String path = "Totally/A/Path";
    String dummyString = "";

    Coordinate dummyCoord = new Coordinate(0, 0);
    Coordinate coord = new Coordinate(10, 10);
    Coordinate[] badCoords = new Coordinate[3];
    badCoords[0] = new Coordinate(-10, 10);
    badCoords[1] = new Coordinate(10, -10);
    badCoords[2] = new Coordinate(-1, -1);

    Dimension size = new Dimension(10, 10);
    Dimension dummySize = new Dimension(1, 1);
    Dimension[] badSizes = new Dimension[3];
    badSizes[0] = new Dimension(0, 0);
    badSizes[1] = new Dimension(-10, 10);
    badSizes[2] = new Dimension(10, -10);

    myInfo.addImage(dummyString, dummyCoord, dummySize);
    myInfo.addImage(path, coord, size);

    for (Coordinate bad : badCoords) {
      try {
        myInfo.addImage(dummyString, bad, size);
      } catch (RuntimeException err) {
        dummyErrorMessage(err);
      }
    }

    for (Dimension badSize : badSizes) {
      try {
        myInfo.addImage(dummyString, coord, badSize);
      } catch (RuntimeException err) {
        dummyErrorMessage(err);
      }
    }

    ImmutableBoardInfo immutInfo = new ImmutableBoardInfo(myInfo);
    ArrayList<BoardImage> test = new ArrayList<>(immutInfo.getBoardImages());

    assertEquals(test.size(), 2);
    assertEquals(test.get(1).imagePath(), path);
    assertEquals(test.get(1).location(), coord);
    assertEquals(test.get(1).size(), size);
  }

  @Test
  void testAddingSize() {
    Dimension notused = new Dimension(100, 100);
    Dimension actual = new Dimension(100, 200);
    Dimension bad = new Dimension(-75, 200);
    Dimension otherBad = new Dimension(100, -56);

    myInfo.setBoardSize(notused);

    myInfo.setBoardSize(actual);

    try {
      myInfo.setBoardSize(bad);
    } catch (RuntimeException err) {
      dummyErrorMessage(err);
    }

    try {
      myInfo.setBoardSize(otherBad);
    } catch (RuntimeException err) {
      dummyErrorMessage(err);
    }

    ImmutableBoardInfo immutInfo = new ImmutableBoardInfo(myInfo);
    Dimension returnedSize = immutInfo.getBoardSize();

    assertEquals(returnedSize, actual);
  }

  private void dummyErrorMessage(RuntimeException err) {
    System.out.println(err.getMessage() + " : " + err.getCause());
  }

}
