package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

public interface ParameterStrategy {

    Node renderInput();

    void saveInput();

    boolean isInputValid();

    Metadata getMetadata();

    Attribute getAttribute();
}
