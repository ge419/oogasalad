package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import java.util.List;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.TileLandedEvent;

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
    eventEventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(), actionFactory.makeCheckTileWinAction(landedTileId, winningTileIds));
  }
}
