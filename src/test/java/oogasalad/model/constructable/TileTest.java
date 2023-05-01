package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TileTest {
  SchemaDatabase testSchemaDB;
  ObjectMapper testObjectMapper;
  Tile testTile;

  @BeforeEach
  void setup(){
    testObjectMapper = new ObjectMapper();
    testSchemaDB =new SimpleSchemaDatabase(testObjectMapper);
    testTile = new Tile(testSchemaDB);
  }

  @Test
  void testDefaultValue(){
    assertNotNull(testTile.getHeight());
    assertNotNull(testTile.getWidth());
    assertNotNull(testTile.getNextTileIds());
    assertNotNull(testTile.getInfo());
    assertNotNull(testTile.getCoordinate());
    assertNotNull(testTile.getY());
    assertNotNull(testTile.getX());
    assertNotNull(testTile.getAngle());
    assertNotNull(testTile.getAllAttributes());
    assertNotNull(testTile.viewTypeAttribute());
    assertNotNull(testTile.typeAttribute());
    assertNotNull(testTile.colorAttribute());

  }

  @Test
  void testSettingTileOwnerId(){
    assertThrows(NoSuchElementException.class, ()->
        testTile.getOwnerId());
//    testTile.setOwnerId("owner1");
//    assertEquals(testTile.getOwnerId(), "owner1");
     }

  @Test
  void testPriceAttributeNotCreatedUntilTileNeedsPriceAttribute() {
    assertThrows(NoSuchElementException.class, ()->
        testTile.getPrice());
    assertThrows(NoSuchElementException.class, ()->
        testTile.getPriceAttribute());
//    testTile.setPrice(5.7);
//  assertNotNull(testTile.getPrice());
  }

}