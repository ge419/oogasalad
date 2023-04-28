package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.events.ChooseNumberOfPlayerPiecesEvent;
import oogasalad.model.engine.events.StartTurnEvent;

public class NumberOfPlayerPieceRule extends AbstractGameConstruct implements EditableRule{

  public static final String SCHEMA_NAME = "numberOfPiecesPerPlayerRule";

  private final GameHolder gameHolder;

  protected NumberOfPlayerPieceRule(String baseSchema,
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(ChooseNumberOfPlayerPiecesEvent.class, this::setPlayerPieces);
  }

  private void setPlayerPieces(EventHandlerParams<ChooseNumberOfPlayerPiecesEvent> eventHandlerParams){
    
  }
}
