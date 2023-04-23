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

public class BooleanParameterStrategy implements ParameterStrategy, BuilderUtility {

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
    public Node renderInput(ResourceBundle resourceBundle, Pane form) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        element = (CheckBox) makeCheckBox(name);
        element.setSelected(attr.getValue());
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
