package oogasalad.view.builder.popupform;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import oogasalad.model.attribute.ColorAttribute;
import oogasalad.model.attribute.ColorMetadata;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class ColorParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private ColorParameterStrategy colorParameterStrategy;
    private ColorMetadata meta;
    private ColorAttribute attr;

    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String attributeKey = "attr";
        meta = new ColorMetadata(attributeKey);
        meta.setName(attributeKey);
        attr = meta.makeColorAttribute();
        colorParameterStrategy = new ColorParameterStrategy(attr, meta);
        VBox root = new VBox();
        root.getChildren().add(colorParameterStrategy.renderInput(resourceBundle, root, ""));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void renderInput() {
        Color val = Color.RED;
        String hexVal = "0xff0000ff";
        ColorPicker field = lookup("#attr").query();
        setValue(field, val);
        assertEquals(field.getValue().toString(), hexVal);
    }

    @Test
    void saveInput() {
        Color val = Color.RED;
        String hexVal = "0xff0000ff";
        ColorPicker field = lookup("#attr").query();
        setValue(field, val);
        colorParameterStrategy.saveInput();
        assertEquals(attr.getValue(), hexVal);
    }

    @Test
    void isInputValid() {
        Color val = Color.RED;
        ColorPicker field = lookup("#attr").query();
        setValue(field, val);
        assertEquals(true, colorParameterStrategy.isInputValid());
    }

    @Test
    void getMetadata() {
        ColorMetadata colorMetadata = (ColorMetadata) colorParameterStrategy.getMetadata();
        assertEquals(colorMetadata.getKey(), meta.getKey());
        assertEquals(colorMetadata.getName(), meta.getName());
    }

    @Test
    void getAttribute() {
        ColorAttribute colorAttribute = (ColorAttribute) colorParameterStrategy.getAttribute();
        assertEquals(colorAttribute.getKey(), attr.getKey());
    }
}