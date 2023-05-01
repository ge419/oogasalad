package oogasalad.view.gameplay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.view.Renderable;
import oogasalad.view.Textable;
import org.junit.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class InterfaceTest extends ApplicationTest implements Textable, Renderable {

  private static final double WRAPPING_BUFFER = 1.5;
  Rectangle testHappyRectangle = new Rectangle();
  Rectangle testSadRectangle = new Rectangle();


  @Override
  public void start(Stage stage) {
  }

  @Test
  public void testResizeText() {
    Text text = new Text("Test Text");
    double componentHeight = 100;
    double componentWidth = 200;
    double scale = 2.0;

    // Call the method
    resizeText(text, componentHeight, scale, componentWidth);

    // Verify that the text is properly resized
    assertEquals(componentWidth / WRAPPING_BUFFER, text.getWrappingWidth(), 0.01);
    assertEquals(TextAlignment.CENTER, text.getTextAlignment());
    assertNotEquals(1.0, text.getScaleX());
    assertNotEquals(1.0, text.getScaleY());
  }

  @Override
  public VBox createTextBox(List info, double height, double width) {
    return null;
  }

  @Test
  public void testRender() {
    BorderPane root = new BorderPane();

    render(root);

    // Verify that the component is added to the center of the root BorderPane
    assertNotNull(root.getCenter());
    assertEquals(testHappyRectangle, root.getCenter());
    assertNotEquals(testSadRectangle, root.getCenter());
  }

  @Override
  public void render(BorderPane pane) {
    pane.setCenter(testHappyRectangle);
  }
}
