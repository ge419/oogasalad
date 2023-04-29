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
        Label nameLabel = new Label("Name this element");

        // Create a text field to edit the image name
        TextField nameField = new TextField(thisCustomElement.getName());
        nameField.setOnAction(event -> thisCustomElement.setName(nameField.getText()));

        infoBox.getChildren().add(nameLabel);
        infoBox.getChildren().add(nameField);

        infoBox.getChildren().addAll(imageSpecificNodes);

        // Create buttons to send image to front or back
        Button toFrontButton = new Button("Send to Front");
        toFrontButton.setOnAction(event -> ((Node) thisCustomElement).toFront());
        Button toBackButton = new Button("Send to Back");
        toBackButton.setOnAction(event -> ((Node) thisCustomElement).toBack());

        CheckBox editableCheckBox = createEditableCheckBox(thisCustomElement, nameField);

        infoBox.getChildren().addAll(Arrays.asList(toFrontButton, toBackButton, editableCheckBox));



        return infoBox;
    }

   private static CheckBox createEditableCheckBox(CustomElement customElement, TextField namefield) {
      CheckBox editableCheckBox = new CheckBox("Editable");
      editableCheckBox.setSelected(customElement.isEditable() && !customElement.getName().isEmpty());
      editableCheckBox.setVisible(!customElement.getName().isEmpty());

      editableCheckBox.setOnAction(event -> {
         boolean selected = editableCheckBox.isSelected();
         customElement.setEditable(selected);
      });

      namefield.setOnAction(e -> {
         if (namefield.getText().isEmpty()) {
            editableCheckBox.setVisible(false);
         } else {
            editableCheckBox.setVisible(true);
         }
      });
      return editableCheckBox;
   }
}
