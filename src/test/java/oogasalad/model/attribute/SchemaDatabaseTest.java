package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.model.engine.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchemaDatabaseTest {

  SchemaDatabase db;

  @BeforeEach
  void setUp() {
    db = new SchemaDatabase();
  }

  @Test
  void resourceSchemas() {
    assertTrue(db.containsSchema("basicTile"));
    assertTrue(db.containsSchema("buyTileRule"));
    assertTrue(db.containsSchema("buyTileRule-tile"));
  }

  @Test
  void appliedSchema() {
    // Apply buyTileRule to basicTile
    SchemaBinding binding = new SchemaBinding("basicTile", "buyTileRule-tile");

    Rule mockRule = mock(Rule.class);
    when(mockRule.appliedSchemasProperty()).thenReturn(
        new SimpleListProperty<>(FXCollections.observableList(List.of(binding))));
    when(mockRule.getAppliedSchemas()).thenReturn(List.of(binding));
    db.setRuleListProperty(FXCollections.observableList(List.of(mockRule)));

    ObjectSchema schema = db.getSchema("basicTile").get();
    
    assertTrue(schema.getMetadata("position").isPresent());
    assertTrue(schema.getMetadata("price").isPresent());

    schema = db.getSchema("buyTileRule").get();
    assertTrue(schema.getMetadata("tileType").isPresent());
    assertTrue(schema.getMetadata("price").isEmpty());
  }
}