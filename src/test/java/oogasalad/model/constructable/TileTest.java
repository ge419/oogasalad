package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.view.Coordinate;
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
    assertNotNull(testTile.getNextTileIds());
    assertNotNull(testTile.getInfo());
    assertNotNull(testTile.getCoordinate());
    assertNotNull(testTile.getAllAttributes());
    assertNotNull(testTile.viewTypeAttribute());
    assertNotNull(testTile.typeAttribute());
    assertNotNull(testTile.colorAttribute());
    assertNotNull(testTile.getViewType());
    assertNotNull(testTile.getType());
  }

  @Test
  void testSettingDefaultAttributes(){
    assertEquals(testTile.getType(), "default");
    testTile.setX(32.8);
    testTile.setY(31.8);
    testTile.setAngle(7.6);
    testTile.setCoordinate(new Coordinate(3.4,5.6));
    testTile.setHeight(5.6);
    testTile.setWidth(3.7);
    testTile.setViewType("BASIC");
    assertEquals(testTile.getX(), 3.4);
    assertEquals(testTile.getY(), 5.6);
    assertEquals(testTile.getAngle(), 0.0);
    assertTrue(testTile.getHeight() == 5.6 && testTile.getWidth() == 3.7);
  }

  @Test
  void testSettingTileOwnerId(){
    assertThrows(NoSuchElementException.class, ()->
        testTile.getOwnerId());
    testTile.addSchema(BuyTileRule.APPLIED_SCHEMA_NAME);
    testTile.setOwnerId("owner1");
    assertEquals(testTile.getOwnerId(), "owner1");
     }

  @Test
  void testPriceAttributeNotCreatedUntilTileNeedsPriceAttribute() {
    assertThrows(NoSuchElementException.class, ()->
        testTile.getPrice());
    assertThrows(NoSuchElementException.class, ()->
        testTile.getPriceAttribute());
    testTile.addSchema(BuyTileRule.APPLIED_SCHEMA_NAME);
    testTile.setPrice(5.7);
    assertNotNull(testTile.getPrice());
  }

}