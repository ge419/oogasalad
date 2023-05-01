package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.removal.LowScoreRemovalStrategy;
import oogasalad.model.engine.actions.removal.PlayerRemovalStrategy;
import oogasalad.model.engine.actions.removal.RemovedPlayerTileResetStrategy;
import oogasalad.model.engine.actions.removal.TileResetStrategy;
import oogasalad.model.engine.events.StartTurnEvent;

/**
 * Rule that outlines removing players from the game due to low score.
 *
 * @Author Jay Yoon
 */
public class RemovePlayerRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "removePlayerRule";
  public static final String SCORE_MIN_BOUND = "scoreMinBound";
  private final ActionFactory actionFactory;

  @Inject
  protected RemovePlayerRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
  }

  /**
   * Listens for a {@link StartTurnEvent} to run {@link #removePlayers(EventHandlerParams)}
   *
   * <p>
   * retrieves the lower score bound for player to continue playing game uses
   * {@link PlayerRemovalStrategy} to update GameHolder Players uses {@link TileResetStrategy} to
   * set tiles unowned adds
   * {@link oogasalad.model.engine.actions.removal.CheckAndRemovePlayerAction} to action queue for
   * potential players removal
   * </p>
   *
   * @param registrar provides event registration methods
   */
  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartTurnEvent.class, this::removePlayers);
  }

  protected void removePlayers(EventHandlerParams<StartTurnEvent> eventEventHandlerParams) {
    int scoreMinBound = IntAttribute.from(this.getAttribute(SCORE_MIN_BOUND).get()).getValue();
    PlayerRemovalStrategy playerRemovalStrategy = new LowScoreRemovalStrategy();
    TileResetStrategy tileResetStrategy = new RemovedPlayerTileResetStrategy();
    eventEventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(),
        actionFactory.makeCheckAndRemovePlayerAction(scoreMinBound, playerRemovalStrategy,
            tileResetStrategy));
  }
}
