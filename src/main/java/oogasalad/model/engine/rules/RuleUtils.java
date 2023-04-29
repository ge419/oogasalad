package oogasalad.model.engine.rules;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.model.attribute.SchemaBinding;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.GameConstruct;

public final class RuleUtils {

  public static final String TILE_TYPE_ATTRIBUTE = "tileType";

  private RuleUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Applies the given schema name to the value of the rule attribute specified by
   * {@link RuleUtils#TILE_TYPE_ATTRIBUTE}.
   */
  public static ListProperty<SchemaBinding> bindToTileType(GameConstruct rule,
      String appliedSchemaName) {
    return bindToAttribute(
        StringAttribute.from(rule.getAttribute(TILE_TYPE_ATTRIBUTE).get()), appliedSchemaName);
  }

  /**
   * Returns a {@link ListProperty<SchemaBinding>} that binds the schema name in a provided field to
   * an applied schema.
   *
   * @param sink              attribute containing name of sink schema
   * @param appliedSchemaName name of schema to apply to sink
   */
  public static ListProperty<SchemaBinding> bindToAttribute(StringAttribute sink,
      String appliedSchemaName) {
    ListProperty<SchemaBinding> bindings = new SimpleListProperty<>(
        FXCollections.observableArrayList());

    bindings.add(new SchemaBinding(sink.getValue(), appliedSchemaName));
    sink.valueProperty().addListener((observable, oldValue, newValue) ->
        bindings.setAll(new SchemaBinding(newValue, appliedSchemaName)));

    return bindings;
  }
}
