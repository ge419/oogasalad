package oogasalad.model.engine.actions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.events.GameEndEvent;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import oogasalad.model.engine.prompt.AIPrompter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

class CheckStandingWinActionTest {

  public static final int LAST_N_STANDING = 3;
  private GameHolder gameHolder;
  @Mock
  private Player player1;
  @Mock private Player player2;
  @Mock private Player player3;
  private Players players;
  @Mock private EventEmitter emitter;
  private ActionParams actionParams;
  private CheckStandingWinAction action;
  private ResourceBundle bundle;

  @BeforeEach
  public void setUp() {
    gameHolder = new GameHolder();
    player1 = mock(Player.class);
    player2 = mock(Player.class);
    player3 = mock(Player.class);
    players = new Players(List.of(player1, player2, player3));
    gameHolder.setPlayers(players);

    emitter = mock(EventEmitter.class);
    bundle = mock(ResourceBundle.class);
    when(bundle.getString(any(String.class))).thenReturn("");
    actionParams = new ActionParams(emitter, new AIPrompter());
    action = new CheckStandingWinAction(gameHolder, LAST_N_STANDING, bundle);
  }

  @Test
  public void emitsGameEndEventIfSatisfiedN() {
    action.runAction(actionParams);
    ArgumentCaptor<GameEndEvent> argument = ArgumentCaptor.forClass(GameEndEvent.class);

    verify(emitter).emit(argument.capture());
    assertEquals(GameEndEvent.class, argument.getValue().getClass());
  }

  @Test
  public void notEmitGameEventEventIfNotSatisfied() {
    Player player4 = mock(Player.class);
    players = new Players(List.of(player1, player2, player3, player4));
    gameHolder.setPlayers(players);

    action = new CheckStandingWinAction(gameHolder, LAST_N_STANDING, bundle);
    action.runAction(actionParams);

    ArgumentCaptor<GameEndEvent> argument = ArgumentCaptor.forClass(GameEndEvent.class);
    verify(emitter, Mockito.never()).emit(argument.capture());


  }

}