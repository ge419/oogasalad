package oogasalad.view.builder;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

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
    default Node makeButton(String property, ResourceBundle resourceBundle, EventHandler<ActionEvent> event) {
        Button btn = new Button(resourceBundle.getString(property));
        btn.setOnAction(event);
        btn.getStyleClass().add("button");
        btn.setId(property);
        return btn;
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
}
