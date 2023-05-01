package oogasalad.model.engine.rules;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.CheckTileWinAction;
import oogasalad.model.engine.actions.wins.CheckWinAndEndAction;
import oogasalad.model.engine.actions.wins.TileWinningStrategy;
import oogasalad.model.engine.actions.wins.WinningConditionStrategy;
import oogasalad.model.engine.events.TileLandedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class FiniteBoardWinRuleTest {

  public static final String E_1 = "195";
  public static final String E_2 = "200";
  public static final String WINNING_TILES = "winningTiles";
  private SchemaDatabase db;
  private ActionFactory mockActionFactory;
  private ActionQueue actionQueue;
  private FiniteBoardWinRule rule;
  private CheckWinAndEndAction mockedAction;
  private EventHandlerParams<TileLandedEvent> eventEventHandlerParams;
  private Attribute winningTiles;
  private ArrayList<String> ids;
  private static final String LANDED_TILE_ID = "001";

  @Before
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(CheckWinAndEndAction.class);
    when(mockActionFactory.makeCheckWinStateAction(any(WinningConditionStrategy.class))).thenReturn(mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    db = injector.getInstance(SchemaDatabase.class);

    Tile mockedTileLanded = mock(Tile.class);
    when(mockedTileLanded.getId()).thenReturn(LANDED_TILE_ID);
    Piece mockedPiece = mock(Piece.class);
    actionQueue = mock(ActionQueue.class);

    eventEventHandlerParams = new EventHandlerParams<>(
        new TileLandedEvent(mockedPiece, mockedTileLanded),
        actionQueue
    );
    rule = new FiniteBoardWinRule(db, mockActionFactory);
    ids = new ArrayList<>(List.of(E_1, E_2));
    winningTiles = new TileListAttribute(WINNING_TILES, ids);
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
  public void makesCheckTileWinAction() {
    rule.checkTileWin(eventEventHandlerParams);

    WinningConditionStrategy winningConditionStrategy = new TileWinningStrategy(LANDED_TILE_ID, ids);
    verify(mockActionFactory).makeCheckWinStateAction(winningConditionStrategy);
  }

  @Test
  public void addsActionToQueue() {
    rule.checkTileWin(eventEventHandlerParams);

    verify(actionQueue).add(Priority.MOST_HIGH.getValue(), mockedAction);
  }

}
