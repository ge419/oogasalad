package oogasalad.model.engine.rules;

import javafx.beans.property.ListProperty;
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
  public static void bindToTileType(
      GameConstruct rule,
      String appliedSchemaName,
      ListProperty<SchemaBinding> bindingsProperty
  ) {
    bindToAttribute(
        StringAttribute.from(rule.getAttribute(TILE_TYPE_ATTRIBUTE).get()),
        appliedSchemaName,
        bindingsProperty);
  }

  /**
   * Returns a {@link ListProperty<SchemaBinding>} that binds the schema name in a provided field to
   * an applied schema.
   *
   * @param sink              attribute containing name of sink schema
   * @param appliedSchemaName name of schema to apply to sink
   */
  public static void bindToAttribute(
      StringAttribute sink,
      String appliedSchemaName,
      ListProperty<SchemaBinding> bindingsProperty
  ) {
    bindingsProperty.setAll(new SchemaBinding(sink.getValue(), appliedSchemaName));
    sink.valueProperty().addListener((observable, oldValue, newValue) ->
        bindingsProperty.setAll(new SchemaBinding(newValue, appliedSchemaName)));
  }
}
