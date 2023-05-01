package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.PieceChosenEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumberOfPieceRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "numberOfPiecesPerPlayerRule";

  private static final Logger LOGGER = LogManager.getLogger(NumberOfPlayersRule.class);
  private final GameHolder gameHolder;
  private final ActionFactory actionFactory;

  @Inject
  protected NumberOfPieceRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder,
      @JacksonInject ActionFactory actionFactory) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(PieceChosenEvent.class, this::setPlayerPieces);
  }

  protected void setPlayerPieces(EventHandlerParams<PieceChosenEvent> eventHandlerParams) {
    eventHandlerParams.actionQueue()
        .add(Priority.MOST_HIGH.getValue(), actionFactory.makeCreatePlayerPieceAction());
  }
}
