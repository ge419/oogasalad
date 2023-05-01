package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import oogasalad.model.attribute.Metadata;

public interface CustomElement {

  static CustomElement load(JsonObject jsonObject) {
    String type = jsonObject.get("type").getAsString().strip();
    try {
      Class<?> clazz = Class.forName(CustomElement.class.getPackage().getName() + "." + type);
      if (!CustomElement.class.isAssignableFrom(clazz)) {
        throw new RuntimeException("Class " + type + " does not implement CustomElement interface");
      }
      Constructor<?> constructor = clazz.getConstructor(JsonObject.class);
      return (CustomElement) constructor.newInstance(jsonObject);
    } catch (Exception e) {
      throw new RuntimeException("Error loading CustomElement", e);
    }
  }

  JsonObject save(Path folderPath) throws IOException;

  VBox getInfo(ResourceBundle languageBundle);

  void setLocation();

  int getIndex();

  String getName();

  void setName(String text);

  boolean isEditable();

  void setEditable(boolean selected);

  Metadata getMetaData();

  void setValue(String loadedValue);

  default VBox makeVbox(CustomElement thisCustomElement, List<Node> imageSpecificNodes) {
    VBox infoBox = new VBox();

    // Create a label for the image name field
    Label nameLabel = new Label("Name this element");

    // Create a text field to edit the image name
    TextField nameField = new TextField(thisCustomElement.getName());
    nameField.setOnKeyReleased(event -> thisCustomElement.setName(nameField.getText()));

    infoBox.getChildren().add(nameLabel);
    infoBox.getChildren().add(nameField);

    infoBox.getChildren().addAll(imageSpecificNodes);

    // Create buttons to send image to front or back
    Button toFrontButton = new Button("Send to Front");
    toFrontButton.setOnAction(event -> ((Node) thisCustomElement).toFront());
    Button toBackButton = new Button("Send to Back");
    toBackButton.setOnAction(event -> ((Node) thisCustomElement).toBack());

    Button deleteButton = new Button("Delete Element");
    deleteButton.setOnAction(event -> delete(thisCustomElement));

    CheckBox editableCheckBox = createEditableCheckBox(thisCustomElement, nameField);
    infoBox.getChildren()
        .addAll(Arrays.asList(toFrontButton, toBackButton, editableCheckBox, deleteButton));

    return infoBox;
  }

  private void delete(CustomElement customElement) {
    ((StackPane) ((Node) customElement).getParent()).getChildren().remove(customElement);
  }

  private CheckBox createEditableCheckBox(CustomElement customElement, TextField namefield) {
    CheckBox editableCheckBox = new CheckBox("Editable");
    editableCheckBox.setSelected(customElement.isEditable() && !customElement.getName().isEmpty());
    editableCheckBox.setVisible(!customElement.getName().isEmpty());

    editableCheckBox.setOnAction(event -> {
      boolean selected = editableCheckBox.isSelected();
      customElement.setEditable(selected);
    });

    namefield.setOnAction(e -> {
      editableCheckBox.setVisible(!namefield.getText().isEmpty());
    });
    return editableCheckBox;
  }

}