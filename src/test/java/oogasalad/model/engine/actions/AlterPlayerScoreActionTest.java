package oogasalad.model.engine.actions;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.GameHolderModule;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.prompt.AIPrompter;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.prompt.StringPromptOption;
import oogasalad.view.tabexplorer.userpreferences.Languages;
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



