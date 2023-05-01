package oogasalad.model.engine.actions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ResourceBundle;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.scores.AlterPlayerScoreAction;
import oogasalad.model.engine.prompt.AIPrompter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlterPlayerScoreActionTest {

  private static final double DELTA_SCORE = 10.0;
  private static final String PLAYER_ID = "player1";
  private Player player;
  private ResourceBundle resourceBundle;
  private ActionParams actionParams;
  private AlterPlayerScoreAction alterPlayerScoreAction;

  @BeforeEach
  public void setUp() {
    resourceBundle = mock(ResourceBundle.class);
    when(resourceBundle.getString(any(String.class))).thenReturn("");
    player = mock(Player.class);
  }

  @Test
  public void testUpdateScore() {
    AlterPlayerScoreAction action = new AlterPlayerScoreAction(player, 100, resourceBundle);
    ActionParams params = new ActionParams(
        mock(EventEmitter.class),
        new AIPrompter()
    );

    when(player.getScore()).thenReturn(2000.0);

    action.runAction(params);

    verify(player).setScore(2100);
  }
}



