package oogasalad.model.engine.rules;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyListProperty;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.PlayerAttribute;
import oogasalad.model.attribute.SchemaBinding;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.BuyAction;
import oogasalad.model.engine.events.TileLandedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class BuyTileRuleTest {

  private BuyTileRule buyRule;
  private ResourceBundle bundle;
  private SchemaDatabase db;
  private GameHolder gameHolder;

  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new AttributeModule());
    db = injector.getInstance(SchemaDatabase.class);

    bundle = mock(ResourceBundle.class);
    when(bundle.getString(any(String.class))).thenReturn("");

    Player player = new Player(db);
    gameHolder = new GameHolder();
    gameHolder.setPlayers(new Players(List.of(player)));
    gameHolder.setCurrentPlayer(player);

    buyRule = new BuyTileRule(db, gameHolder, bundle);
  }

  @Test
  public void listensToTileLandedEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    buyRule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(TileLandedEvent.class), handlerCaptor.capture());

  }

  @Test
  public void testAppliedSchemasProperty() {
    ReadOnlyListProperty<SchemaBinding> appliedSchemas = buyRule.appliedSchemasProperty();
    assertNotNull(appliedSchemas);
    assertEquals(1, appliedSchemas.size());
  }

  @Test
  public void cannotBuyWithoutMoney() {
    Tile tile = new Tile(db);
    Piece piece = new Piece(db);
    BuyAction buyAction = mock(BuyAction.class);
    ActionQueue actionQueue = new SimpleActionQueue();
    EventHandlerParams<TileLandedEvent> params = new EventHandlerParams<>(new TileLandedEvent(piece, tile), actionQueue);
    buyRule.tryBuyProp(params);
    assertTrue(actionQueue.isEmpty());
  }





}