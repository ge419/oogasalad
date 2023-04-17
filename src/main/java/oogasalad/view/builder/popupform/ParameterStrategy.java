package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

public interface ParameterStrategy {

    Node renderInput();

    boolean validateInput();

    Metadata getMetadata();

    Attribute getAttribute();
}
