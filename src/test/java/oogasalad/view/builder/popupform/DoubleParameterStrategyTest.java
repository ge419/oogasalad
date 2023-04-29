package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.Tile;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

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
        Tile myGameConstruct = new Tile(new SchemaDatabase());
        root.getChildren().add(doubleParameterStrategy.renderInput(resourceBundle, root,
            myGameConstruct.getId()));
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