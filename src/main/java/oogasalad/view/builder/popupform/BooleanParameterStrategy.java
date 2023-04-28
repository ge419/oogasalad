package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.BooleanMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

/**
 * A strategy used by the popup form to display a form element when
 * a boolean is required from the user via user input. This consists
 * of one labeled checkbox for the user to input true or false.
 * Example Usage:
 * VBox form = new VBox();
 * BooleanParameterStrategy strategy = new BooleanParameterStrategy(myBooleanAttribute, myBooleanMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick
 */
public class BooleanParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final BooleanAttribute attr;
    private final BooleanMetadata meta;
    private CheckBox element;
    /**
     * Creates an instance of BooleanParameterStrategy
     * Can be used to display form data to a user for a bool,
     * validate the input, save to an attribute, and access
     * the corresponding BooleanAttribute and BooleanMetadata
     * @param attr BooleanAttribute
     * @param meta BooleanMetadata
     */
    @Inject
    public BooleanParameterStrategy(
        @Assisted Attribute attr,
        @Assisted Metadata meta) {
        this.attr = BooleanAttribute.from(attr);
        this.meta = BooleanMetadata.from(meta);
    }
    /**
     * Returns a JavaFX form element for a boolean attribute
     * @param resourceBundle
     * @param form parent pane of element
     * @return HBox containing labeled JavaFX checkbox
     */
    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        element = (CheckBox) makeCheckBox(name);
        element.setSelected(attr.getValue());
        return makeHBox(String.format("%sBooleanInput", name), textLabel, element);
    }
    /**
     * Saves input to instance's PositionAttribute
     */
    @Override
    public void saveInput() {
        attr.setValue(getFieldValue());
    }
    /**
     * Uses metadata to validate user input
     * @return boolean (true means input is valid)
     */
    @Override
    public boolean isInputValid() {
        return meta.isValidValue(getFieldValue());
    }
    /**
     * Gets BooleanMetadata
     * @return BooleanMetadata
     */
    @Override
    public Metadata getMetadata() {
        return meta;
    }
    /**
     * Gets BooleanAttribute
     * @return BooleanAttribute
     */
    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private boolean getFieldValue() {
        return element.isSelected();
    }
}
