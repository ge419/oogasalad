package oogasalad.view.builder.popupform;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.DoubleMetadata;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class DoubleParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private DoubleParameterStrategy doubleParameterStrategy;
    private DoubleMetadata meta;
    private DoubleAttribute attr;
    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String attributeKey = "attr";
        meta = new DoubleMetadata(attributeKey);
        meta.setName(attributeKey);
        attr = meta.makeDoubleAttribute();
        doubleParameterStrategy = new DoubleParameterStrategy(attr, meta);
        VBox root = new VBox();
        root.getChildren().add(doubleParameterStrategy.renderInput(resourceBundle, root, ""));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Test
    void renderInput() {
        double val = 0.125;
        Spinner<Double> field = lookup("#attr").query();
        setValue(field, val);
        assertEquals(field.getValue(), val);
    }
    @Test
    void isInputValid() {
        double val = 0.125;
        Spinner<Double> field = lookup("#attr").query();
        setValue(field, val);
        assertEquals(true, doubleParameterStrategy.isInputValid());
    }
    @Test
    void saveInput() {
        double val = 0.125;
        Spinner<Double> field = lookup("#attr").query();
        setValue(field, val);
        doubleParameterStrategy.saveInput();
        assertEquals(attr.getValue(), val);
    }
    @Test
    void getMetadata() {
        DoubleMetadata doubleMeta = (DoubleMetadata) doubleParameterStrategy.getMetadata();
        assertEquals(doubleMeta.getKey(), meta.getKey());
        assertEquals(doubleMeta.getName(), meta.getName());
    }
    @Test
    void getAttribute() {
        DoubleAttribute doubleAttr = (DoubleAttribute) doubleParameterStrategy.getAttribute();
        assertEquals(doubleAttr.getKey(), attr.getKey());
    }
}