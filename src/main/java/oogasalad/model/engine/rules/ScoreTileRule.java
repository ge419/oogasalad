package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.TileLandedEvent;

public class ScoreTileRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "scoreTileRule";
  public static final int NEG = -1;
  private final ActionFactory actionFactory;
  private final GameHolder gameholder;

  @Inject
  protected ScoreTileRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameholder,
      @JacksonInject ActionFactory actionFactory
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
    this.gameholder = gameholder;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(TileLandedEvent.class, this::alterPlayerScore);
  }

  private void alterPlayerScore(EventHandlerParams<TileLandedEvent> eventHandlerParams) {
    Tile tile = eventHandlerParams.event().landedTile();
    Player currentPlayer = gameholder.getCurrentPlayer();
    String ownerId = tile.getOwnerId();
    if (tile.isOwned() && !ownerId.equals(currentPlayer.getId())) {
      Player ownerPlayer = gameholder.getPlayers().getById(ownerId).get();
      double deltaScore = tile.getPrice();
      eventHandlerParams.actionQueue().add(
          Priority.HIGH.getValue(), actionFactory.makeAlterPlayerScoreAction(currentPlayer, deltaScore* NEG));
      eventHandlerParams.actionQueue().add(Priority.HIGH.getValue(), actionFactory.makeAlterPlayerScoreAction(ownerPlayer, deltaScore));
    }
  }
}
