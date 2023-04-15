package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import java.util.ResourceBundle;

/**
 * This will get replaced with more specific choices, like TileChoice or something...
 */

class ChoiceParameterStrategy implements ParameterStrategy, BuilderUtility {
    public ChoiceParameterStrategy(){}
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        return null;
    }
    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean validateInput(Metadata metadata) {
        return true;
    }

    @Override
    public void setValue(Attribute attribute) {
        //???Attribute.from(attribute).setValue(getValue());
    }
}
