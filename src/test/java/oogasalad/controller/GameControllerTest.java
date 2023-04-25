package oogasalad.controller;

import static org.junit.jupiter.api.Assertions.*;

import oogasalad.model.attribute.SchemaDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameControllerTest {
  SchemaDatabase db;

  @BeforeEach
  void setUp() {
    db = new SchemaDatabase();
  }

  @Test
  void loadSchemas() {
    assertTrue(db.getSchema("basicTile").isPresent());
    assertEquals(5, db.getAllSchemaNames().size());
  }

  @Test
  void loadTilesDeserialization() {

  }

  @Test
  void loadTilesValidValues() {

  }

  @Test
  void loadPlayersDeserialization() {

  }

}