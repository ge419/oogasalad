package oogasalad.view.builder.customTile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class CustomTileBuilderViewTest extends DukeApplicationTest {

  private CustomTileBuilder builder;

  @BeforeClass
  public static void initToolkit() {
    new JFXPanel(); // initializes JavaFX environment
  }

  @Override
  public void start(Stage stage) {
    builder = new CustomTileBuilder();
    builder.start(stage);
  }

  @Test
  public void testLeftPaneWidth() {
    assertEquals(300, CustomTileBuilder.getLeftPaneWidth());
  }

  @Test
  public void testLeftPaneRender() {
    assertNotNull(lookup("#renameButton").query());
    assertNotNull(lookup("#saveButton").query());
    assertNotNull(lookup("#loadButton").query());
    assertNotNull(lookup("#addImageButton").query());
    assertNotNull(lookup("#addTextButton").query());
    assertNotNull(lookup("#addColorButton").query());
  }

  @Test
  public void testRenameButton() {
    // Given

    // When
    clickOn("#renameButton");
    //extInputDialog dialog = lookup(".dialog-pane").queryAs(TextInputDialog.class);
    write("newTitle");
    //clickOn(dialog.getDialogPane().lookupButton(ButtonType.OK));

    //assertEquals(this.builder.getName(), stage.getTitle());
  }


}
