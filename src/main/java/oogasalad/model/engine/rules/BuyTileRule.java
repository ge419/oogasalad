package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.PlayerAttribute;
import oogasalad.model.attribute.SchemaBinding;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.BuyAction;
import oogasalad.model.engine.events.TileLandedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuyTileRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "buyTileRule";
  public static final String APPLIED_SCHEMA_NAME = "buyTileRule-tile";
  public static final String PRICE_ATTRIBUTE = "price";
  public static final String OWNER_ATTRIBUTE = "owner";
  private static final Logger LOGGER = LogManager.getLogger(BuyTileRule.class);
  private final ListProperty<SchemaBinding> appliedSchemaProperty;
  private final GameHolder gameHolder;

  @Inject
  public BuyTileRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder
  ) {
    super(SCHEMA_NAME, database);
    appliedSchemaProperty = RuleUtils.bindToTileType(this, APPLIED_SCHEMA_NAME);
    this.gameHolder = gameHolder;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(TileLandedEvent.class, this::tryBuyProp);
  }

  @Override
  public ReadOnlyListProperty<SchemaBinding> appliedSchemasProperty() {
    return appliedSchemaProperty;
  }

  private void tryBuyProp(EventHandlerParams<TileLandedEvent> eventHandlerParams) {
    Tile tile = eventHandlerParams.event().landedTile();
    Piece piece = eventHandlerParams.event().piece();
    Player player = gameHolder.getCurrentPlayer();

    PlayerAttribute owner = getOwnedAttribute(tile);
    DoubleAttribute price = getPriceAttribute(tile);
    double newMoney = player.getScore() - price.getValue();

    if (owner.getId().isEmpty() && newMoney >= 0) {
      LOGGER.info("Prompted User to Buy Property with Remaining Money {}", newMoney);
      BuyAction buyAction = new BuyAction(() -> {
        tile.setOwned();
        tile.setOwnerId(player.getId());
        owner.setId(piece.getPlayer().get().getId());
        player.setScore(newMoney);
      });
      eventHandlerParams.actionQueue().add(1, buyAction);
    }
  }

  private PlayerAttribute getOwnedAttribute(Tile tile) {
    return PlayerAttribute.from(
        tile.getAttribute(OWNER_ATTRIBUTE).get()
    );
  }

  private DoubleAttribute getPriceAttribute(Tile tile) {
    return DoubleAttribute.from(
        tile.getAttribute(PRICE_ATTRIBUTE).get()
    );
  }
}
