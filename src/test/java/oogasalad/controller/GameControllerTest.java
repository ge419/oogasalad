package oogasalad.controller;

import java.lang.reflect.Field;
import javafx.stage.Stage;
import oogasalad.model.engine.SimpleEngine;
import oogasalad.view.gameplay.Die;
import oogasalad.view.gameplay.HumanPrompter;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.Rule;
import oogasalad.view.ViewFactory;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.gameplay.SetDieRule;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameControllerTest {

  private GameController gameController;
  @Mock private Stage stage;
  @Mock private Gameview mockGameview;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException, IOException {
    gameController = new GameController(Paths.get("data", "0hbvOqXKOQdhpgu3aLIO"), Languages.ENGLISH.getLocaleStr());
    stage = mock(Stage.class);
    mockGameview = mock(Gameview.class);
    Field gvField = gameController.getClass().getDeclaredField("gv");
    gvField.setAccessible(true);
    gvField.set(gameController, mockGameview);
    try {
      gameController.setGame(stage);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSetGame() throws IOException {

  }

  @Test
  public void testRun() {

  }

  @Test
  public void testDoEffectWithEmptyEffects() {
    gameController.doEffect();
    verify(gameController).run();
  }

  @Test
  public void testDoEffectWithNonEmptyEffects() {

  }

}
