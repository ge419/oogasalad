package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.inject.Inject;

import oogasalad.model.attribute.*;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BuilderUtility;

public class PositionParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final PositionAttribute attr;
    private final PositionMetadata meta;
    private Spinner<Double> xElement;
    private Spinner<Double> yElement;
    private Spinner<Double> angleElement;
    private static final double minValue = -999;
    private static final double maxValue = 999;
    private double initValue = 0;
    private static final double minAngle = -360;
    private static final double maxAngle = 360;
    private double initAngle = 0;

    @Inject
    public PositionParameterStrategy(
            @Assisted Attribute attr,
            @Assisted Metadata meta) {
        this.attr = PositionAttribute.from(attr);
        this.meta = PositionMetadata.from(meta);
    }

    @Override
    public Node renderInput(ResourceBundle resourceBundle) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        Node xLabel = new Text("X");
        xElement = (Spinner<Double>) makeDoubleSpinner(name+"X", minValue, maxValue, initValue);
        Node yLabel = new Text("Y");
        yElement = (Spinner<Double>) makeDoubleSpinner(name+"Y", minValue, maxValue, initValue);
        Node angleLabel = new Text("Angle");
        angleElement = (Spinner<Double>) makeDoubleSpinner(name+"Angle", minAngle, maxAngle, initAngle);
        VBox elementContainer = new VBox(new HBox(xLabel, xElement), new HBox(yLabel, yElement), new HBox(angleLabel, angleElement));
        return makeHBox(String.format("%sPositionInput", name), textLabel, elementContainer);
    }

    @Override
    public void saveInput() {
        attr.setCoordinate(new Coordinate(xElement.getValue(), yElement.getValue(), angleElement.getValue()));
    }

    @Override
    public boolean isInputValid() {
        return meta.isValidCoordinate(getFieldValue());
    }

    @Override
    public Metadata getMetadata() {
        return meta;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private Coordinate getFieldValue() {
        return new Coordinate(xElement.getValue(), yElement.getValue(), angleElement.getValue());
    }
}
