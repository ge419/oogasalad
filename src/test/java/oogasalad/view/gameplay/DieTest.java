package oogasalad.view.gameplay;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class DieTest extends ApplicationTest {

  @Override
  public void start(Stage stage) {
    BorderPane pane = new BorderPane();
    Die die = new Die();
    die.render(pane);
    Scene scene = new Scene(pane, 800, 800);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testDieVisibility() {
    FxAssert.verifyThat("#Die", isVisible());
    for (Node dot : lookup(".Circle").queryAll()) {
      FxAssert.verifyThat(dot, isVisible());
    }
  }
}
