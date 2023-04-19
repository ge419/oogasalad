package oogasalad.view.builder;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import oogasalad.view.Coordinate;

public interface BuilderUtility {

  default Node makeText(String property, ResourceBundle resourceBundle) {
    Text text = new Text(resourceBundle.getString(property));
    text.setId(property);
    text.getStyleClass().add("text");
    return text;
  }

  default Node makeDropdown(String property, ObservableList<String> choices,
      EventHandler<ActionEvent> event) {
    ComboBox comboBox = new ComboBox();
    comboBox.setItems(choices);
    comboBox.getSelectionModel().select(0);
    comboBox.setOnAction(event);
    comboBox.setId(property);
    return comboBox;
  }

  default Node makeButton(String property, ResourceBundle resourceBundle,
      EventHandler<ActionEvent> event) {
    Button btn = new Button(resourceBundle.getString(property));
    btn.setOnAction(event);
    btn.getStyleClass().add("button");
    btn.setId(property);
    return btn;
  }

  default MenuItem makeMenuItem(String property, ResourceBundle resourceBundle,
      EventHandler<ActionEvent> event){
    MenuItem item = new MenuItem(resourceBundle.getString(property));
    item.setOnAction(event);
    item.getStyleClass().add("menuItem");
    item.setId(property);
    return item;
  }

  default Node makePane(String property, double width, double height) {
    Pane canvas = new Pane();
    canvas.setPrefSize(width, height);
    canvas.setId(property);
    return canvas;
  }

  default Node makeVBox(String property, Node... nodes) {
    VBox vBox = new VBox(nodes);
    vBox.getStyleClass().add("vBox");
    vBox.setId(property);
    return vBox;
  }

  default Node makeHBox(String property, Node... nodes) {
    HBox hBox = new HBox(nodes);
    hBox.getStyleClass().add("hBox");
    hBox.setId(property);
    return hBox;
  }

  default Node makeTextField(String property) {
    TextField textField = new TextField();
    textField.setId(property);
    return textField;
  }

  default Node makeColorPicker(String property) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId(property);
    return colorPicker;
  }

  default FileChooser makeFileChooser(String property, ResourceBundle resourceBundle) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(resourceBundle.getString(property));
    return fileChooser;
  }

  default DirectoryChooser makeDirectoryChooser(String property, ResourceBundle resourceBundle) {
    DirectoryChooser directChooser = new DirectoryChooser();
    directChooser.setTitle(resourceBundle.getString(property));
    return directChooser;
  }

  default Node makeCheckBox(String property, ResourceBundle resourceBundle){
    CheckBox checker = new CheckBox(resourceBundle.getString(property));
    checker.getStyleClass().add("checkbox");
    checker.setId(property);
    return checker;
  }

  default Node makeFileSelectButton(String property, ResourceBundle resourceBundle,
      FileChooser fileChooser) {
    return makeButton(property, resourceBundle,
        e -> Optional.ofNullable(fileChooser.showOpenDialog(null)));
  }

  default Optional<File> fileLoad(ResourceBundle resourceBundle, String property) {
    FileChooser chooseFile = makeFileChooser(property, resourceBundle);
    return Optional.ofNullable(chooseFile.showOpenDialog(null));
  }

  default Optional<File> fileSave(ResourceBundle resourceBundle, String property) {
    FileChooser chooseFile = makeFileChooser(property, resourceBundle);
    return Optional.ofNullable(chooseFile.showSaveDialog(null));
  }

  default Optional<File> directoryGet(ResourceBundle resourceBundle, String property) {
    DirectoryChooser directFile = makeDirectoryChooser(property, resourceBundle);
    return Optional.ofNullable(directFile.showDialog(null));
  }

  default void setNodeLocation(Node node, Coordinate coord) {
    node.setLayoutX(coord.getXCoor());
    node.setLayoutY(coord.getYCoor());
  }
}
