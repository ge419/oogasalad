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
    assertTrue(db.getSchema("tile").isPresent());
    assertEquals(7, db.getAllSchemaNames().size());
  }

  @Test
  void engineEffectsAddition() {

  }

}