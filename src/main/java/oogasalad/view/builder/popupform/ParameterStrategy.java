package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.constructable.GameConstruct;

import java.util.ResourceBundle;

public interface ParameterStrategy {
    Node renderInput(String name, ResourceBundle resourceBundle);
    Object getValue();
    boolean validateInput(Metadata metadata);
    void setValue(Attribute attribute);
}
