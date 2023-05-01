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
 * Rule that outlines winning status of finite board game.
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

  /**
   * Listens for a {@link TileLandedEvent} to run {@link #checkTileWin(EventHandlerParams)}
   *
   * <p>
   * retrieves list of winning tiles IDs from rule attribute and compares with landed tile uses
   * {@link TileWinningStrategy} as winning condition strategy to check for winning condition adds
   * {@link oogasalad.model.engine.actions.wins.CheckWinAndEndAction} to action queue for potential
   * game end
   * </p>
   *
   * @param registrar provides event registration methods
   */
  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(TileLandedEvent.class, this::checkTileWin);
  }

  protected void checkTileWin(EventHandlerParams<TileLandedEvent> eventEventHandlerParams) {
    List<String> winningTileIds = TileListAttribute.from(this.getAttribute(WINNING_TILES).get())
        .getTileIds();
    String landedTileId = eventEventHandlerParams.event().landedTile().getId();
    WinningConditionStrategy winningCondition = new TileWinningStrategy(landedTileId,
        winningTileIds);
    eventEventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(),
        actionFactory.makeCheckWinStateAction(winningCondition));
  }
}
