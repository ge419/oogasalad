package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import java.util.List;
import javafx.beans.property.ReadOnlyListProperty;
import oogasalad.model.attribute.SchemaBinding;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumberOfPlayersRule extends AbstractGameConstruct implements EditableRule{

  public static final String SCHEMA_NAME = "numberOfPlayersRule";
  private static final Logger LOGGER = LogManager.getLogger(NumberOfPlayersRule.class);
  private final GameHolder gameHolder;

  protected NumberOfPlayersRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {

  }

  @Override
  public List<SchemaBinding> getAppliedSchemas() {
    return EditableRule.super.getAppliedSchemas();
  }

  @Override
  public ReadOnlyListProperty<SchemaBinding> appliedSchemasProperty() {
    return EditableRule.super.appliedSchemasProperty();
  }
}
