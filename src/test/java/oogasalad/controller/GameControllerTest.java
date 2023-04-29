package oogasalad.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {
  SchemaDatabase db;

  @BeforeEach
  void setUp() {
    db = new SimpleSchemaDatabase();

  }

  @Test
  void loadSchemas() {
    assertTrue(db.getSchema("tile").isPresent());
  }

  @Test
  void engineEffectsAddition() {

  }

}