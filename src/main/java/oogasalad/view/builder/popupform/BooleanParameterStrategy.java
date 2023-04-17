package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.view.builder.BuilderUtility;

import javax.inject.Inject;
import java.util.ResourceBundle;

class BooleanParameterStrategy implements ParameterStrategy, BuilderUtility {
    private final BooleanAttribute attr;
    private final BooleanMetadata meta;
    private CheckBox element;
    @Inject
    public BooleanParameterStrategy(
            @Assisted Attribute attr,
            @Assisted Metadata meta) {
        this.attr = BooleanAttribute.from(attr);
        this.meta = BooleanMetadata.from(meta);
    }
    @Override
    public Node renderInput(ResourceBundle resourceBundle) {
        String name = meta.getName();
        Node textLabel = new Text(name+" (True/False)");
        element = (CheckBox) makeCheckBox(name);
        return makeHBox(String.format("%sBooleanInput", name), textLabel, element);
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

    private boolean getFieldValue() {
        return element.isSelected();
    }
}
