package oogasalad.model.engine.rules;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.CheckTileWinAction;
import oogasalad.model.engine.events.TileLandedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class FiniteBoardWinRuleTest {

  private SchemaDatabase db;
  private ActionFactory mockActionFactory;
  private FiniteBoardWinRule rule;
  private ActionQueue actionQueue;

  @Before
  public void setUp() {
    CheckTileWinAction mockedAction = mock(CheckTileWinAction.class);
    mockActionFactory = mock(ActionFactory.class);
    when(mockActionFactory.makeCheckTileWinAction(any(String.class), any(List.class))).thenReturn(mockedAction);
    Injector injector = Guice.createInjector(new AttributeModule());
    db = injector.getInstance(SchemaDatabase.class);
    actionQueue = new SimpleActionQueue();
    Tile mockedTileLanded = mock(Tile.class);
    when(mockedTileLanded.getId()).thenReturn("0");

    EventHandlerParams<TileLandedEvent> params = mock(EventHandlerParams.class);
    when(params.actionQueue()).thenReturn(actionQueue);
    rule = new FiniteBoardWinRule(db, mockActionFactory);

    TileListAttribute winningTileAttribute = TileListAttribute.from(
        rule.getAttribute("winningTiles").get());
    winningTileAttribute.setTileIds(List.of("195", "200"));
  }

  @Test
  public void listensToTileLandedEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(TileLandedEvent.class), handlerCaptor.capture());
  }

  @Test
  public void testCheckTileWin_withWinningTile() {

  }

}
