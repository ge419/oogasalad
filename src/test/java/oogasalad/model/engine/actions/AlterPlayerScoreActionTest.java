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
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.EngineResourceBundle;
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

  @BeforeEach
  public void setUp() {
    Path savePath = mock(Path.class);
    resourceBundle = mock(ResourceBundle.class);
    when(resourceBundle.getString(any(String.class))).thenReturn("");
    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        new SaveManagerModule(savePath),
        new AttributeModule(),
        new EngineModule(Languages.ENGLISH.getLocaleStr()),
        binder -> binder.bind(Prompter.class).toInstance(new AIPrompter())
    );
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);
    player = new Player(db);
    player.setName(PLAYER_ID);
    Prompter prompter = mock(Prompter.class);
    actionParams = new ActionParams(null, prompter);
  }

  @Test
  public void testRunAction() {
    AlterPlayerScoreAction action = new AlterPlayerScoreAction(player, DELTA_SCORE, resourceBundle);
    action.runAction(actionParams);

    String expectedPromptMessage = String.format(
        resourceBundle.getString(AlterPlayerScoreAction.class.getSimpleName()), PLAYER_ID, DELTA_SCORE);

    StringPromptOption promptOption = new StringPromptOption(
        resourceBundle.getString(AlterPlayerScoreAction.class.getSimpleName() + "Prompt1"));
  }

  @Test
  public void testUpdateScore() {
    AlterPlayerScoreAction action = new AlterPlayerScoreAction(player, DELTA_SCORE, resourceBundle);
    ActionParams params = mock(ActionParams.class);
    Prompter prompterMock = mock(Prompter.class);
    when(params.prompter()).thenReturn(prompterMock);
    action.runAction(params);
    assertEquals(Optional.of(2000.0), player.getScore());
  }
}



