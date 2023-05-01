package oogasalad.view.builder.popupform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.StringMetadata;
import oogasalad.model.constructable.Tile;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class TextParameterStrategyTest extends DukeApplicationTest {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
  private static final String ROOT_ID = "BoardPane";
  private TextParameterStrategy textParameterStrategy;
  private StringMetadata meta;
  private StringAttribute attr;

  @Override
  public void start(Stage stage) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle(
        BASE_RESOURCE_PACKAGE + "en-USBuilderText");
    String attributeKey = "attr";
    meta = new StringMetadata(attributeKey);
    meta.setName(attributeKey);
    attr = meta.makeStringAttribute();
    textParameterStrategy = new TextParameterStrategy(attr, meta);
    VBox root = new VBox();
    root.setId(ROOT_ID);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    Tile tileMock = mock(Tile.class);
    root.getChildren().add(textParameterStrategy.renderInput(resourceBundle, root,
        tileMock.getId()));
  }

  @Test
  void renderInput() {
    TextField field = lookup("#attr").query();
    writeInputTo(field, "Hello World!");
    assertEquals(field.getText(), "Hello World!");
  }

  @Test
  void isInputValid() {
    TextField field = lookup("#attr").query();
    writeInputTo(field, "Hello World!");
    assertTrue(textParameterStrategy.isInputValid());
  }

  @Test
  void saveInput() {
    String val = "Hello World";
    TextField field = lookup("#attr").query();
    writeInputTo(field, val);
    textParameterStrategy.saveInput();
    assertEquals(attr.getValue(), val);
  }

  @Test
  void getMetadata() {
    StringMetadata strMeta = (StringMetadata) textParameterStrategy.getMetadata();
    assertEquals(strMeta.getKey(), meta.getKey());
    assertEquals(strMeta.getName(), meta.getName());
  }

  @Test
  void getAttribute() {
    StringAttribute stringAttribute = (StringAttribute) textParameterStrategy.getAttribute();
    assertEquals(stringAttribute.getKey(), attr.getKey());
  }
}