package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class CustomElementHelper extends VBox {


     static VBox makeVbox(CustomElement thisCustomElement, List<Node> imageSpecificNodes) {
        VBox infoBox = new VBox();

        // Create a label for the image name field
        Label imageNameLabel = new Label("Name this element");

        // Create a text field to edit the image name
        TextField imageNameField = new TextField(thisCustomElement.getName());
        imageNameField.setOnAction(event -> thisCustomElement.setName(imageNameField.getText()));

        infoBox.getChildren().add(imageNameLabel);
        infoBox.getChildren().add(imageNameField);

        infoBox.getChildren().addAll(imageSpecificNodes);

        // Create buttons to send image to front or back
        Button toFrontButton = new Button("Send to Front");
        toFrontButton.setOnAction(event -> ((Node) thisCustomElement).toFront());
        Button toBackButton = new Button("Send to Back");
        toBackButton.setOnAction(event -> ((Node) thisCustomElement).toBack());

        CheckBox checkBox = new CheckBox("Editable");
        checkBox.setSelected(true); // set initial value
        checkBox.setVisible(!thisCustomElement.getName().isEmpty()); // set visibility based on name value

        infoBox.getChildren().addAll(Arrays.asList(toFrontButton, toBackButton, checkBox));


        return infoBox;
    }

   private CheckBox createEditableCheckBox(CustomElement customElement) {
      CheckBox editableCheckBox = new CheckBox("Editable");
      editableCheckBox.setSelected(customElement.isEditable() && !customElement.getName().isEmpty());
      editableCheckBox.setVisible(!customElement.getName().isEmpty());

      editableCheckBox.setOnAction(event -> {
         boolean selected = editableCheckBox.isSelected();
         customElement.setEditable(selected);
      });

      return editableCheckBox;
   }
}
