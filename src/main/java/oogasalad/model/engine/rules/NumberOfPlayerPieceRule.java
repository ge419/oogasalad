package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.ChooseNumberOfPlayerPiecesEvent;
import oogasalad.model.engine.events.StartTurnEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumberOfPlayerPieceRule extends AbstractGameConstruct implements EditableRule{

  public static final String SCHEMA_NAME = "playerPieceRule";

  private static final Logger LOGGER = LogManager.getLogger(NumberOfPlayerPieceRule.class);
  private final GameHolder gameHolder;
  private final ActionFactory actionFactory;

  @Inject
  protected NumberOfPlayerPieceRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder,
      @JacksonInject ActionFactory actionFactory) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(ChooseNumberOfPlayerPiecesEvent.class, this::setPlayerPieces);
  }

  private void setPlayerPieces(EventHandlerParams<ChooseNumberOfPlayerPiecesEvent> eventHandlerParams){
    eventHandlerParams.actionQueue().add(1, actionFactory.makeCreatePlayerPieceAction());
  }
}
