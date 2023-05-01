package oogasalad.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import oogasalad.model.engine.Engine;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class GameControllerTest {

  private GameController gameController;
  private ResourceBundle bundle;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException, IOException {
    gameController = new GameController(Languages.ENGLISH.getLocaleStr(),
        Paths.get("data","games", "0hbvOqXKOQdhpgu3aLIO"));
  }

  @Test
  public void testSetGame() {

  }

  @Test
  public void testRun() {
  }

  @Test
  public void testDoEffectWithEmptyEffects() {

  }

  @Test
  public void testDoEffectWithNonEmptyEffects() {

  }

}
