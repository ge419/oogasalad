package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

class IntegerParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final Attribute attr;
    private final Metadata meta;
    private TextField element = new TextField();

    @Inject
    public IntegerParameterStrategy(
        @Assisted Attribute attr,
        @Assisted Metadata meta) {
        this.attr = attr;
        this.meta = meta;
    }

    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name + " (Integer)");
        element = (TextField) makeTextField(name);
        return makeHBox(String.format("%sIntegerInput", name), textLabel, element);
    }

    @Override
    public boolean validateInput(Metadata metadata) {
        if (getValue() == null) {
            return false;
        }
        IntMetadata intMetadata = (IntMetadata) metadata;
        return getValue() > intMetadata.getMinValue() && getValue() < intMetadata.getMaxValue();
    }
    @Override
    public Integer getValue() {
        try {
            int num = Integer.parseInt(element.getText());
            return num;
        } catch (NumberFormatException nfe) {
            System.out.println("Integer not provided in integer input");
        }
        return null;
    }
    @Override
    public void setValue(Attribute attribute) {
        IntAttribute.from(attribute).setValue(getValue());
    }
}
