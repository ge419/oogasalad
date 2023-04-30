package oogasalad.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import javafx.stage.Stage;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class GameControllerTest {

  private GameController gameController;
  @Mock private Stage stage;
  @Mock private Gameview mockGameview;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException, IOException {
    gameController = new GameController(Languages.ENGLISH.getLocaleStr(),
        Paths.get("data", "0hbvOqXKOQdhpgu3aLIO"));
    stage = mock(Stage.class);
    mockGameview = mock(Gameview.class);
    Field gvField = gameController.getClass().getDeclaredField("gv");
    gvField.setAccessible(true);
    gvField.set(gameController, mockGameview);
    gameController.setGame(stage);
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
