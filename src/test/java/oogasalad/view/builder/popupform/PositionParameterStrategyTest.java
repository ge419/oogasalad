package oogasalad.view.builder.popupform;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.PositionMetadata;
import oogasalad.view.Coordinate;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class PositionParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private PositionParameterStrategy positionParameterStrategy;
    private PositionMetadata meta;
    private PositionAttribute attr;
    private static final String ROOT_ID = "BoardPane";
    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String attributeKey = "attr";
        meta = new PositionMetadata(attributeKey);
        meta.setName(attributeKey);
        attr = meta.makeCoordinateAttribute();
        positionParameterStrategy = new PositionParameterStrategy(attr, meta);
        VBox root = new VBox();
        root.setId(ROOT_ID);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        root.getChildren().add(positionParameterStrategy.renderInput(resourceBundle, root, ""));
    }
    @Test
    void renderInput() {
        Coordinate val = new Coordinate(0, 1, 2);
        Spinner<Double> xField = lookup("#attrX").query();
        Spinner<Double> yField = lookup("#attrY").query();
        Spinner<Double> angleField = lookup("#attrAngle").query();
        setValue(xField, val.getXCoor());
        setValue(yField, val.getYCoor());
        setValue(angleField, val.getAngle());
        assertEquals(xField.getValue(), val.getXCoor());
        assertEquals(yField.getValue(), val.getYCoor());
        assertEquals(angleField.getValue(), val.getAngle());
    }

    @Test
    void saveInput() {
        Coordinate val = new Coordinate(0, 1, 2);
        Spinner<Double> xField = lookup("#attrX").query();
        Spinner<Double> yField = lookup("#attrY").query();
        Spinner<Double> angleField = lookup("#attrAngle").query();
        setValue(xField, val.getXCoor());
        setValue(yField, val.getYCoor());
        setValue(angleField, val.getAngle());
        positionParameterStrategy.saveInput();
        assertEquals(attr.getCoordinate(), val);
    }

    @Test
    void isInputValid() {
        Coordinate val = new Coordinate(0, 1, 2);
        Spinner<Double> xField = lookup("#attrX").query();
        Spinner<Double> yField = lookup("#attrY").query();
        Spinner<Double> angleField = lookup("#attrAngle").query();
        setValue(xField, val.getXCoor());
        setValue(yField, val.getYCoor());
        setValue(angleField, val.getAngle());
        assertEquals(true, positionParameterStrategy.isInputValid());
    }

    @Test
    void getMetadata() {
        PositionMetadata posMeta = (PositionMetadata) positionParameterStrategy.getMetadata();
        assertEquals(posMeta.getKey(), meta.getKey());
        assertEquals(posMeta.getName(), meta.getName());
    }

    @Test
    void getAttribute() {
        PositionAttribute positionAttribute = (PositionAttribute) positionParameterStrategy.getAttribute();
        assertEquals(positionAttribute.getKey(), attr.getKey());
    }
}