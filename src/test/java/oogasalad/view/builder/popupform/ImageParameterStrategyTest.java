package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.*;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ImageParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private ImageParameterStrategy imageParameterStrategy;
    private ImageMetadata meta;
    private ImageAttribute attr;
    @Override
    public void start(Stage stage) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
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