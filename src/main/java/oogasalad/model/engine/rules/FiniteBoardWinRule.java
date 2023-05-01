package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import java.util.List;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.wins.TileWinningStrategy;
import oogasalad.model.engine.actions.wins.WinningConditionStrategy;
import oogasalad.model.engine.events.TileLandedEvent;

/**
 * Action for creating players upon user selection after launching game.
 * <p>
 *   User selects from the prompt the number of players to play the game
 *   Added to the engine action queue by {@link oogasalad.model.engine.rules.NumberOfPlayersRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class FiniteBoardWinRule extends AbstractGameConstruct implements EditableRule {

  private static final String SCHEMA_NAME = "finiteBoardWinRule";
  public static final String WINNING_TILES = "winningTiles";
  private final ActionFactory actionFactory;

  @Inject
  protected FiniteBoardWinRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(TileLandedEvent.class, this::checkTileWin);
  }

  protected void checkTileWin(EventHandlerParams<TileLandedEvent> eventEventHandlerParams) {
    List<String> winningTileIds = TileListAttribute.from(this.getAttribute(WINNING_TILES).get()).getTileIds();
    String landedTileId = eventEventHandlerParams.event().landedTile().getId();
    WinningConditionStrategy winningCondition = new TileWinningStrategy(landedTileId, winningTileIds);
    eventEventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(), actionFactory.makeCheckWinStateAction(winningCondition));
  }
}
