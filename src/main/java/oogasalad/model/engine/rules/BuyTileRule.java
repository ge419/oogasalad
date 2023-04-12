package oogasalad.model.engine.rules;

import javax.inject.Inject;
import javax.inject.Provider;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.BuyAction;
import oogasalad.model.engine.events.MonopolyEvent;
import oogasalad.view.tiles.Tile;
import oogasalad.view.tiles.Tiles;

public class BuyTileRule implements Rule {

  private final Tiles tiles;

  @Inject
  public BuyTileRule(Tiles tiles) {
    this.tiles = tiles;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(MonopolyEvent.LANDED, this::tryBuyProp);
  }

  private void tryBuyProp(EventHandlerParams eventHandlerParams) {
    int tileId = TileAttribute.from(eventHandlerParams.event().attributeMap().get("tile"))
        .getValue();
    Tile tile = tiles.getTile(tileId);
    if (!tile.isOwned()) {
      eventHandlerParams.actionQueue().add(1, new BuyAction(tile));
    }
  }
}
