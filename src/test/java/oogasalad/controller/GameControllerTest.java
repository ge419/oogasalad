package oogasalad.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import org.junit.Before;
import org.junit.Test;

public class GameControllerTest {

  private GameController gameController;
  private ResourceBundle bundle;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException, IOException {
    gameController = new GameController(Languages.ENGLISH.getLocaleStr(),
        Paths.get("data", "games", "0hbvOqXKOQdhpgu3aLIO"));
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
