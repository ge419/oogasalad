package oogasalad.view.builder.popupform;

import javafx.stage.Stage;
import oogasalad.model.attribute.ImageAttribute;
import oogasalad.model.attribute.ImageMetadata;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class ImageParameterStrategyTest extends DukeApplicationTest {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
  private ImageParameterStrategy imageParameterStrategy;
  private ImageMetadata meta;
  private ImageAttribute attr;

  @Override
  public void start(Stage stage) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "en-USBuilderText");
//        String attributeKey = "attr";
//        meta = new ImageMetadata(attributeKey);
//        meta.setName(attributeKey);
//        attr = meta.makeImageAttribute();
//        imageParameterStrategy = new ImageParameterStrategy(attr, meta);
//        VBox root = new VBox();
//        root.getChildren().add(imageParameterStrategy.renderInput(resourceBundle, root));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
  }

  @Test
  void renderInput() {

  }

  @Test
  void saveInput() {

  }

  @Test
  void isInputValid() {

  }

  @Test
  void getMetadata() {

  }

  @Test
  void getAttribute() {

  }
}