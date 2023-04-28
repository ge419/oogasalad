package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.inject.Inject;

import oogasalad.model.attribute.*;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.events.TileEvent;
import oogasalad.view.tiles.ViewTile;

/**
 * A strategy used by the popup form to display a form element when
 * a position is required from the user via user input. This consists
 * of three labeled double spinners for the user to input X, Y, and Angle values.
 * Additionally, an event handler is placed on the window to automatically
 * change the value displayed in the spinner depending on the location when dragged.
 * A key assumption is that the strategy assumes the form is editing a draggable element
 * Example Usage:
 * VBox form = new VBox();
 * PositionParameterStrategy strategy = new PositionParameterStrategy(myPositionAttribute, myPositionMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick
 */
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
    private static final String ROOT_ID = "#BoardPane";
    private Pane root;

    /**
     * Creates an instance of PositionParameterStrategy
     * Can be used to display form data to a user for coordinates,
     * validate the input, save to an attribute, and access
     * the corresponding PositionAttribute and PositionMetadata
     * @param attr PositionAttribute
     * @param meta PositionMetadata
     */
    @Inject
    public PositionParameterStrategy(
            @Assisted Attribute attr,
            @Assisted Metadata meta) {
        this.attr = PositionAttribute.from(attr);
        this.meta = PositionMetadata.from(meta);
    }

    /**
     * Returns a JavaFX form element for a coordinate attribute
     * @param resourceBundle
     * @param form parent pane of element
     * @return HBox containing labeled JavaFX spinners for X, Y, Angle
     */
    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        Node xLabel = new Text("X");
        xElement = (Spinner<Double>) makeDoubleSpinner(name+resourceBundle.getString("X"), minValue, maxValue, attr.getX());
        Node yLabel = new Text("Y");
        yElement = (Spinner<Double>) makeDoubleSpinner(name+resourceBundle.getString("Y"), minValue, maxValue, attr.getY());
        Node angleLabel = new Text("Angle");
        angleElement = (Spinner<Double>) makeDoubleSpinner(name+resourceBundle.getString("Angle"), minAngle, maxAngle, attr.getAngle());
        VBox elementContainer = new VBox(new HBox(xLabel, xElement), new HBox(yLabel, yElement), new HBox(angleLabel, angleElement));
        Scene scene = form.getScene();
        this.root = (Pane) scene.lookup(ROOT_ID);
        addHandlerToRoot();
        return makeHBox(String.format("%sPositionInput", name), textLabel, elementContainer);
    }
    private void addHandlerToRoot() {
        root.addEventHandler(TileEvent.DRAG_TILE, event -> {
            ViewTile tile = event.getViewTile();
            xElement.getValueFactory().setValue(tile.asNode().getBoundsInParent().getMinX());
            yElement.getValueFactory().setValue(tile.asNode().getBoundsInParent().getMinY());
        });
    }

    /**
     * Saves input to instance's PositionAttribute
     */
    @Override
    public void saveInput() {
        attr.setCoordinate(new Coordinate(xElement.getValue(), yElement.getValue(), angleElement.getValue()));
    }

    /**
     * Uses metadata to validate user input
     * @return boolean (true means input is valid)
     */
    @Override
    public boolean isInputValid() {
        return meta.isValidCoordinate(getFieldValue());
    }

    /**
     * Gets PositionMetadata
     * @return PositionMetadata
     */
    @Override
    public Metadata getMetadata() {
        return meta;
    }

    /**
     * Gets PositionAttribute
     * @return PositionAttribute
     */
    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private Coordinate getFieldValue() {
        return new Coordinate(xElement.getValue(), yElement.getValue(), angleElement.getValue());
    }
}
