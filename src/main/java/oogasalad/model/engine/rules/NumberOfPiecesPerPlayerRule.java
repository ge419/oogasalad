package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventRegistrar;

public class NumberOfPiecesPerPlayerRule extends AbstractGameConstruct implements EditableRule{

  public static final String SCHEMA_NAME = "numberOfPiecesPerPlayerRule";

  private final GameHolder gameHolder;

  protected NumberOfPiecesPerPlayerRule(String baseSchema,
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {

  }
}
