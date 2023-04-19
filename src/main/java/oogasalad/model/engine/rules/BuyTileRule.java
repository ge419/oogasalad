package oogasalad.model.engine.rules;

import javax.inject.Inject;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.BuyAction;
import oogasalad.model.engine.events.TileLandedEvent;

public class BuyTileRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "buyTileRule";

  @Inject
  public BuyTileRule(SchemaDatabase database) {
    super(SCHEMA_NAME, database);
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(TileLandedEvent.class, this::tryBuyProp);
  }

  private void tryBuyProp(EventHandlerParams<TileLandedEvent> eventHandlerParams) {
    Tile tile = eventHandlerParams.event().getLandedTile();
    BooleanAttribute ownedAttribute = BooleanAttribute.from(
        tile.getAttribute(BuyAction.OWNED_ATTRIBUTE));

    if (!ownedAttribute.getValue()) {
      eventHandlerParams.actionQueue().add(1, new BuyAction(tile));
    }
  }
}
