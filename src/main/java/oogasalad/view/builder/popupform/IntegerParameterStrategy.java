package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

class IntegerParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final IntAttribute attr;
    private final IntMetadata meta;
    private Spinner<Integer> element;

    @Inject
    public IntegerParameterStrategy(
        @Assisted Attribute attr,
        @Assisted Metadata meta) {
        this.attr = IntAttribute.from(attr);
        this.meta = IntMetadata.from(meta);
    }

    @Override
    public Node renderInput(ResourceBundle resourceBundle) {
        String name = meta.getName();
        Node textLabel = new Text(name + " (Integer)");
        element = (Spinner<Integer>) makeIntSpinner(meta.getMinValue(), meta.getMaxValue(), meta.getMaxValue());
        return makeHBox(String.format("%sIntegerInput", name), textLabel, element);
    }

    @Override
    public void saveInput() {
        attr.setValue(getFieldValue());
    }

    @Override
    public boolean isInputValid() {
        return meta.isValidValue(getFieldValue());
    }

    @Override
    public Metadata getMetadata() {
        return meta;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private int getFieldValue() {
        return element.getValue();
    }
}
