package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchemaDatabaseTest {

  SchemaDatabase db;

  @BeforeEach
  void setUp() {
    Injector injector = Guice.createInjector(new ObjectMapperModule(), new AttributeModule());
    db = injector.getInstance(SchemaDatabase.class);
  }

  @Test
  void resourceSchemas() {
    assertTrue(db.containsSchema("tile"));
    assertTrue(db.containsSchema("buyTileRule"));
    assertTrue(db.containsSchema("buyTileRule-tile"));
  }

  @Test
  void appliedSchemas() {
    // Apply buyTileRule to tile
    ObjectSchema schema = db.getSchema("tile").get();

    assertTrue(schema.getMetadata("position").isPresent());
    assertTrue(schema.getMetadata("price").isEmpty());

    SchemaBinding binding = new SchemaBinding("tile", "buyTileRule-tile");

    Rule mockRule = mock(Rule.class);
    when(mockRule.appliedSchemasProperty()).thenReturn(
        new SimpleListProperty<>(FXCollections.observableList(List.of(binding))));
    when(mockRule.getAppliedSchemas()).thenReturn(List.of(binding));
    db.setRuleListProperty(FXCollections.observableList(List.of(mockRule)));

    schema = db.getSchema("tile").get();

    assertTrue(schema.getMetadata("position").isPresent());
    assertTrue(schema.getMetadata("price").isPresent());

    schema = db.getSchema("buyTileRule").get();
    assertTrue(schema.getMetadata("tileType").isPresent());
    assertTrue(schema.getMetadata("price").isEmpty());
  }

  @Test
  void appliedVirtualSchema() {
    SchemaBinding binding = new SchemaBinding("monopoly", "buyTileRule-tile");
    ListProperty<SchemaBinding> prop = new SimpleListProperty<>(
        FXCollections.observableArrayList());

    db.setRuleListProperty(FXCollections.observableList(List.of(new TestRule(prop))));

    prop.add(binding);
    ObjectSchema schema = db.getSchema("monopoly").get();

    assertTrue(schema.getMetadata("price").isPresent());
  }

  @Test
  void customSchemas() {
    ObjectSchema schema1 = new SimpleObjectSchema("!s1", List.of());
    ObjectSchema schema2 = new SimpleObjectSchema("!s2", List.of());

    db.addCustomSchema(schema1);
    db.addCustomSchema(schema2);

    assertTrue(db.getSchema("!s1").isPresent());
    assertTrue(db.getSchema("!s2").isPresent());

    List<ObjectSchema> customSchemas = db.getCustomSchemas();
    assertEquals(2, customSchemas.size());
    assertTrue(customSchemas.containsAll(List.of(schema1, schema2)));
  }

  @Test
  void duplicateSchemas() {
    // Adding a schema that conflicts with a resource file will error
    ObjectSchema schema = new SimpleObjectSchema("tile", List.of());
    assertThrows(Exception.class, () -> db.addCustomSchema(schema));

    // Adding a schema can replace an existing custom schema
    ObjectSchema schema1 = new SimpleObjectSchema("!s1", List.of());
    ObjectSchema schema2 = new SimpleObjectSchema("!s1", List.of(new StringMetadata("key")));
    db.addCustomSchema(schema1);
    db.addCustomSchema(schema2);
    assertEquals(schema2, db.getSchema("!s1").get());
  }

  class TestRule implements Rule {

    private final ReadOnlyListProperty<SchemaBinding> prop;

    public TestRule(ReadOnlyListProperty<SchemaBinding> prop) {
      this.prop = prop;
    }

    @Override
    public void registerEventHandlers(EventRegistrar registrar) {

    }

    @Override
    public ReadOnlyListProperty<SchemaBinding> appliedSchemasProperty() {
      return prop;
    }
  }
}