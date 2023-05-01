package oogasalad.model.engine.rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import oogasalad.model.attribute.AttributeModule;
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
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.AlterPlayerScoreAction;
import oogasalad.model.engine.actions.CreatePlayersAction;
import oogasalad.model.engine.events.StartGameEvent;
import oogasalad.model.engine.events.TileLandedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ScoreTileRuleTest {

  private ActionFactory mockActionFactory;
  private AlterPlayerScoreAction mockedAction;
  private ActionQueue mockedQueue;
  private Player player;
  private Player currentPlayer;
  private Piece piece;
  private Tile landedTile;
  private EventHandlerParams<TileLandedEvent> eventHandlerParams;
  private ScoreTileRule rule;
  private GameHolder gameholder;
  private static final double TEST_SCORE = 0;

  @BeforeEach
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(AlterPlayerScoreAction.class);
    mockedQueue = mock(SimpleActionQueue.class);
    player = mock(Player.class);
    currentPlayer = mock(Player.class);
    when(player.getId()).thenReturn("001");
    piece = mock(Piece.class);
    landedTile = mock(Tile.class);

    gameholder = new GameHolder();
    Players players = new Players(List.of(player));
    gameholder.setPlayers(players);
    gameholder.setCurrentPlayer(currentPlayer);

    when(mockActionFactory.makeAlterPlayerScoreAction(player, TEST_SCORE)).thenReturn(mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    eventHandlerParams = new EventHandlerParams<>(
        new TileLandedEvent(piece, landedTile), mockedQueue
    );
    rule = new ScoreTileRule(db, gameholder, mockActionFactory);
  }

  @Test
  public void listensToTileLandedEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(TileLandedEvent.class), handlerCaptor.capture());
  }

  @Test
  public void doesNotMakeAlterScoreActionIfNotOwned() {
    rule.alterPlayerScore(eventHandlerParams);

    verify(mockActionFactory, never()).makeAlterPlayerScoreAction(player, TEST_SCORE);
  }

  @Test
  public void doesNotAddGeneratedActionIfNotOwned() {
    rule.alterPlayerScore(eventHandlerParams);

    verify(mockedQueue, never()).add(Priority.HIGH.getValue(), mockedAction);
  }

  @Test
  public void makeAlterScoreActionIfConditions() {
    when(landedTile.isOwned()).thenReturn(true);
    when(landedTile.getOwnerId()).thenReturn("001");

    eventHandlerParams = new EventHandlerParams<>(
        new TileLandedEvent(piece, landedTile), mockedQueue
    );
    rule.alterPlayerScore(eventHandlerParams);

    verify(mockActionFactory).makeAlterPlayerScoreAction(player, TEST_SCORE);

  }

  @Test
  public void addsGeneratedActionsToQueueIfConditions() {
    when(landedTile.isOwned()).thenReturn(true);
    when(landedTile.getOwnerId()).thenReturn("001");

    eventHandlerParams = new EventHandlerParams<>(
        new TileLandedEvent(piece, landedTile), mockedQueue
    );
    rule.alterPlayerScore(eventHandlerParams);

    verify(mockedQueue).add(Priority.HIGH.getValue(), mockedAction);
  }


}