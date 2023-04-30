package oogasalad.model.engine.rules;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Collections;
import java.util.List;

import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.constructable.Piece;
import oogasalad.model.engine.Event;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.CheckTileWinAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.TileLandedEvent;

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

    Attribute winningTiles = new TileListAttribute("winningTiles", List.of("195", "200"));
    rule.setAllAttributes(List.of(winningTiles));
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
