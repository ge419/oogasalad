package oogasalad.view.builder;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import oogasalad.view.Coordinate;
/**
 * A collection of methods that create JavaFX elements using the provided properties file,
 * and assigns a CSS class and ID to each element. This simplifies JavaFX element creation,
 * and standardizes usage
 * @author Jason Fitzpatrick
 * @author Trevon Helm
 */
public interface BuilderUtility {
  /**
   * Creates a Text object with ID property and value from ResourceBundle -> property
   * @param property string that is a key in the ResourceBundle with value = text content
   * @return Node Text
   */
  default Node makeText(String property, ResourceBundle resourceBundle) {
    Text text = new Text(resourceBundle.getString(property));
    text.setId(property);
    text.getStyleClass().add("text");
    return text;
  }

  /**
   * Creates a ComboBox object with ID = property and options from choices.
   * Assigns an event handler to when a new value is selected
   * @param property string equal to the intended CSS ID
   * @param choices list of options
   * @param event action to occur when new value is selected
   * @return Node ComboBox
   */
  default Node makeDropdown(String property, ObservableList<String> choices,
      EventHandler<ActionEvent> event) {
    ComboBox comboBox = new ComboBox();
    comboBox.setItems(choices);
    comboBox.getSelectionModel().select(0);
    comboBox.setOnAction(event);
    comboBox.setId(property);
    return comboBox;
  }
  /**
   * Creates a Button with ID = property, and text from ResourceBundle -> property
   * Assigns an event handler to when clicked
   * @param property string equal to the intended CSS ID and ResourceBundle key
   * @param event action to occur when clicked
   * @return Node Button
   */
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

  /**
   * Creates a Pane with ID = property of size (width, height)
   * @param property string equal to the intended CSS ID
   * @param width double width of pane
   * @param height double height of pane
   * @return Node Pane
   */
  default Node makePane(String property, double width, double height) {
    Pane canvas = new Pane();
    canvas.setPrefSize(width, height);
    canvas.setId(property);
    return canvas;
  }
  /**
   * Creates a VBox with ID = property and children nodes
   * @param property string equal to the intended CSS ID
   * @param nodes any number of Node objects
   * @return Node VBox
   */
  default Node makeVBox(String property, Node... nodes) {
    VBox vBox = new VBox(nodes);
    vBox.getStyleClass().add("vBox");
    vBox.setId(property);
    return vBox;
  }
  /**
   * Creates a HBox with ID = property and children nodes
   * @param property string equal to the intended CSS ID
   * @param nodes any number of Node objects
   * @return Node HBox
   */
  default Node makeHBox(String property, Node... nodes) {
    HBox hBox = new HBox(nodes);
    hBox.getStyleClass().add("hBox");
    hBox.setId(property);
    return hBox;
  }
  /**
   * Creates a TextField with ID = property
   * @param property string equal to the intended CSS ID
   * @return Node TextField
   */
  default Node makeTextField(String property) {
    TextField textField = new TextField();
    textField.setId(property);
    return textField;
  }

  /**
   * Creates a ColorPicker with ID = property.
   * Assigns an event handler to when a new value is selected
   * @param property string equal to the intended CSS ID
   * @return Node ColorPicker
   */
  default Node makeColorPicker(String property) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId(property);
    return colorPicker;
  }

  /**
   * Creates a Slider with ID = property
   * @param property string equal to the intended CSS ID and ResourceBundle key
   * @return Node Slider
   */
  default Node makeSlider(String property, double min, double max, double intialValue) {
    Slider slider = new Slider(min, max, intialValue);
    slider.setId(property);
    return slider;
  }

  /**
   * Creates a FileChooser with Title = lookup of property in resourceBundle
   * @param property string equal to the intended resourceBundle key for title
   * @param resourceBundle ResourceBundle for title lookup
   * @return Node FileChooser
   */
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
  /**
   * Creates a FileChooser with label = lookup of property in resourceBundle and ID = property
   * @param property string equal to the intended resourceBundle key for label and CSS ID
   * @param resourceBundle ResourceBundle for label lookup
   * @return Node CheckBox
   */
  default Node makeCheckBox(String property, ResourceBundle resourceBundle){
    CheckBox checker = new CheckBox(resourceBundle.getString(property));
    checker.getStyleClass().add("checkbox");
    checker.setId(property);
    return checker;
  }

  /**
   * Creates an unlabeled CheckBox with ID = property
   * @param property string equal to intended CSS ID
   * @return Node CheckBox
   */
  default Node makeCheckBox(String property) {
    CheckBox checkBox = new CheckBox();
    checkBox.setId(property);
    return checkBox;
  }

  default Node makeLabel(String property, ResourceBundle languageBundle) {
    Label label = new Label(languageBundle.getString(property));
    label.setId(property);
    return label;
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

  /**
   * Creates a Text object with ID property and value from ResourceBundle -> property. Wrapping width = wrappingWidth
   * @param property string equal to intended CSS ID and key for text lookup in resourceBundle
   * @param resourceBundle ResourceBundle for text lookup
   * @param wrappingWidth double width of wrapped text
   * @return Node Text
   */
  default Node makeWrappedText(String property, ResourceBundle resourceBundle, double wrappingWidth) {
    Text text = (Text) makeText(property, resourceBundle);
    text.setWrappingWidth(wrappingWidth);
    return text;
  }

  /**
   * Creates an Integer Spinner with ID = property and defined minimum, maximum, and initial values
   * @param property string equal to intended CSS ID
   * @param min int minimum value of spinner
   * @param max int maximum value of spinner
   * @param initial int initial value of spinner
   * @return Node Spinner<Integer>
   */
    default Node makeIntSpinner(String property, int min, int max, int initial) {
      Spinner<Integer> spinner = new Spinner<>();
      SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
          min, max, initial);
      spinner.setValueFactory(valueFactory);
      spinner.setId(property);
      spinner.setEditable(true);
      return spinner;
    }
  /**
   * Creates a Double Spinner with ID = property and defined minimum, maximum, and initial values
   * @param property string equal to intended CSS ID
   * @param min double minimum value of spinner
   * @param max double maximum value of spinner
   * @param initial double initial value of spinner
   * @return Node Spinner<Double>
   */
    default Node makeDoubleSpinner(String property, double min, double max, double initial) {
      Spinner<Double> spinner = new Spinner<>();
      SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, initial);
      spinner.setValueFactory(valueFactory);
      spinner.setId(property);
      spinner.setEditable(true);
      return spinner;
    }

  default String displayMessageWithArguments(ResourceBundle language, String resourceKey,
      Object... arguments) {
    return String.format(language.getString(resourceKey), arguments);
  }
}
