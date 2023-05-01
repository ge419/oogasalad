package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import oogasalad.view.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BoardImageTest {

  BoardImage testBoardImage;
  SchemaDatabase testSchemaDB;
  ObjectMapper testObjectMapper;
  Tile mockTile;

  @BeforeEach
  void setup(){
    mockTile  = Mockito.mock(Tile.class);
    testObjectMapper = new ObjectMapper();
    testSchemaDB =new SimpleSchemaDatabase(testObjectMapper);
    testBoardImage = new BoardImage(testSchemaDB);
  }
  @Test
  void testDefaultAttributes() {
    assertNotNull(testBoardImage.imageAttribute());
    assertNotNull(testBoardImage.positionAttribute());
    assertNotNull(testBoardImage.scaleAttribute());
  }

  @Test
  void testGetImage() {
    assertEquals(testBoardImage.getImage(), "");
  }

  @Test
  void testSetImage() {
    testBoardImage.setImage("image.png");
    assertEquals(testBoardImage.getImage(), "image.png");
  }

  @Test
  void testSetCoordinate() {
    testBoardImage.setCoordinate(new Coordinate(4.7, 3.4));
    assertEquals(testBoardImage.positionAttribute().getCoordinate().getXCoor(), 4.7);
  }
}