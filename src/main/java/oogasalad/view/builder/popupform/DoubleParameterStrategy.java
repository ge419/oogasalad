package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.inject.Inject;

import oogasalad.model.attribute.*;
import oogasalad.view.builder.BuilderUtility;

class DoubleParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final DoubleAttribute attr;
    private final DoubleMetadata meta;
    private Spinner<Double> element;

    @Inject
    public DoubleParameterStrategy(
            @Assisted Attribute attr,
            @Assisted Metadata meta) {
        this.attr = DoubleAttribute.from(attr);
        this.meta = DoubleMetadata.from(meta);
    }

    @Override
    public Node renderInput(ResourceBundle resourceBundle) {
        String name = meta.getName();
        Node textLabel = new Text(name + " (Double)");
        element = (Spinner<Double>) makeDoubleSpinner(name, meta.getMinValue(), meta.getMaxValue(), meta.getMaxValue());
        return makeHBox(String.format("%sDoubleInput", name), textLabel, element);
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

    private double getFieldValue() {
        return element.getValue();
    }
}

