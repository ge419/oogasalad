package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;
/**
 * A strategy used by the popup form to display a form element when
 * an integer is required from the user via user input. This consists
 * of a labeled integer spinner.
 * Example Usage:
 * VBox form = new VBox();
 * IntegerParameterStrategy strategy = new IntegerParameterStrategy(myIntegerAttribute, myIntegerMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick
 */
public class IntegerParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final IntAttribute attr;
    private final IntMetadata meta;
    private Spinner<Integer> element;
    /**
     * Creates an instance of IntegerParameterStrategy
     * Can be used to display form data to a user for an int,
     * validate the input, save to an attribute, and access
     * the corresponding IntegerAttribute and IntegerMetadata
     * @param attr IntegerAttribute
     * @param meta IntegerMetadata
     */
    @Inject
    public IntegerParameterStrategy(
        @Assisted Attribute attr,
        @Assisted Metadata meta) {
        this.attr = IntAttribute.from(attr);
        this.meta = IntMetadata.from(meta);
    }
    /**
     * Returns a JavaFX form element for an integer attribute
     * @param resourceBundle
     * @param form parent pane of element
     * @return HBox containing a labeled integer spinner
     */
    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        element = (Spinner<Integer>) makeIntSpinner(name, meta.getMinValue(), meta.getMaxValue(),
            attr.getValue());
        return makeHBox(String.format("%sIntegerInput", name), textLabel, element);
    }
    /**
     * Saves input to instance's IntegerAttribute
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
     * Gets IntegerMetadata
     * @return IntegerMetadata
     */
    @Override
    public Metadata getMetadata() {
        return meta;
    }
    /**
     * Gets IntegerAttribute
     * @return IntegerAttribute
     */
    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private int getFieldValue() {
        return element.getValue();
    }
}
