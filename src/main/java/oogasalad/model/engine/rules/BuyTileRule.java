package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
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
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.scores.BuyAction;
import oogasalad.model.engine.events.TileLandedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuyTileRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "buyTileRule";
  public static final String APPLIED_SCHEMA_NAME = "buyTileRule-tile";
  public static final String PRICE_ATTRIBUTE = "price";
  public static final String OWNER_ATTRIBUTE = "owner";
  public static final String IMAGE = "image";
  private static final Logger LOGGER = LogManager.getLogger(BuyTileRule.class);
  private final GameHolder gameHolder;
  private final ResourceBundle bundle;
  private ListProperty<SchemaBinding> appliedSchemaProperty;

  @Inject
  public BuyTileRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder,
      @JacksonInject @EngineResourceBundle ResourceBundle bundle
  ) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
    this.bundle = bundle;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(TileLandedEvent.class, this::tryBuyProp);
  }

  @Override
  public ReadOnlyListProperty<SchemaBinding> appliedSchemasProperty() {
    return appliedSchemaProperty;
  }

  @Override
  protected void setAttributeListeners() {
    if (appliedSchemaProperty == null) {
      appliedSchemaProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    RuleUtils.bindToTileType(this, APPLIED_SCHEMA_NAME, appliedSchemaProperty);
  }

  protected void tryBuyProp(EventHandlerParams<TileLandedEvent> eventHandlerParams) {
    Tile tile = eventHandlerParams.event().landedTile();
    Piece piece = eventHandlerParams.event().piece();
    Player player = gameHolder.getCurrentPlayer();

    PlayerAttribute owner = getOwnedAttribute(tile);
    DoubleAttribute price = getPriceAttribute(tile);
    double newMoney = player.getScore() - price.getValue();

    if (owner.getId().isEmpty() && newMoney >= 0 && !tile.getViewType().equals(IMAGE)) {
      LOGGER.info("Prompted User to Buy Property with Remaining Money {}", newMoney);
      BuyAction buyAction = new BuyAction(() -> {
        tile.setOwned();
        tile.setOwnerId(player.getId());
        owner.setId(piece.getPlayer().get().getId());
        player.setScore(newMoney);
      }, bundle);
      eventHandlerParams.actionQueue().add(Priority.HIGH.getValue(), buyAction);
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
