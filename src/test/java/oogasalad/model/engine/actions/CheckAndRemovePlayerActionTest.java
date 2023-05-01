package oogasalad.model.engine.actions;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.removal.CheckAndRemovePlayerAction;
import oogasalad.model.engine.actions.removal.LowScoreRemovalStrategy;
import oogasalad.model.engine.actions.removal.PlayerRemovalStrategy;
import oogasalad.model.engine.actions.removal.RemovedPlayerTileResetStrategy;
import oogasalad.model.engine.actions.removal.TileResetStrategy;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

public class CheckAndRemovePlayerActionTest {

  public static final int SCORE_MIN_BOUND = 50;
  public static final double TO_BE_REMOVED = 40.0;
  public static final double TO_BE_LEFT = 60.0;
  public static final String PLAYER_1 = "player1";
  public static final String PLAYER_2 = "player2";
  private GameHolder gameHolder;
  @Mock private Player player1;
  @Mock private Player player2;
  private Players players;
  @Mock private EventEmitter emitter;
  private ActionParams actionParams;
  private CheckAndRemovePlayerAction action;

  @BeforeEach
  public void setUp() {
    gameHolder = new GameHolder();
    player1 = mock(Player.class);
    player2 = mock(Player.class);
    emitter = mock(EventEmitter.class);
    actionParams = new ActionParams(emitter, null);
    PlayerRemovalStrategy removalStrategy = new LowScoreRemovalStrategy();
    TileResetStrategy tileResetStrategy = new RemovedPlayerTileResetStrategy();
    action = new CheckAndRemovePlayerAction(gameHolder, SCORE_MIN_BOUND, removalStrategy, tileResetStrategy);
    players = new Players(List.of(player1, player2));
    gameHolder.setPlayers(players);

    when(player1.getScore()).thenReturn(TO_BE_REMOVED);
    when(player1.getId()).thenReturn(PLAYER_1);
    when(player2.getScore()).thenReturn(TO_BE_LEFT);
    when(player2.getId()).thenReturn(PLAYER_2);
  }

  @Test
  public void emitsPlayerRemovalEvent() {
    action.runAction(actionParams);
    ArgumentCaptor<PlayerRemovalEvent> argument = ArgumentCaptor.forClass(PlayerRemovalEvent.class);

    verify(emitter).emit(argument.capture());
    assertEquals(PlayerRemovalEvent.class, argument.getValue().getClass());
  }

  @Test
  public void removesPlayerFromGameHolder() {
    action.runAction(actionParams);

    assertEquals(1, gameHolder.getPlayers().getList().size());
  }

  @Test
  public void removesCorrectPlayerSatisfyingCondition() {
    action.runAction(actionParams);

    assertEquals("player2", gameHolder.getPlayers().getList().get(0).getId());
  }
}
