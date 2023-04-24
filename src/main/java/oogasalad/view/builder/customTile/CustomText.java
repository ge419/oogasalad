package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class CustomText extends Label {
    private String name;
    private String defaultContents;
    private int fontSize;
    private boolean bold;

    public CustomText(String name, String defaultContents, int fontSize, boolean bold) {
        this.name = name;
        this.defaultContents = defaultContents;
        this.fontSize = fontSize;
        this.bold = bold;

        // Set the initial properties of the text field
        setText(defaultContents);
        setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultContents() {
        return defaultContents;
    }

    public void setDefaultContents(String defaultContents) {
        this.defaultContents = defaultContents;
        this.setText(defaultContents);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
        setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
    }

    public VBox getInfo() {
        List<Node> nodes = new ArrayList<>();

        // Create a text field to edit the text field name
        TextField textFieldNameField = new TextField(this.name);
        textFieldNameField.setOnAction(event -> setName(textFieldNameField.getText()));
        nodes.add(new Label("Text Field Name:"));
        nodes.add(textFieldNameField);

        // Create a text field to edit the default text displayed
        TextField defaultTextField = new TextField(this.defaultContents);
        defaultTextField.setOnAction(event -> setDefaultContents(defaultTextField.getText()));
        nodes.add(new Label("Default Contents:"));
        nodes.add(defaultTextField);

        // Create a slider to adjust the font size
        Slider fontSizeSlider = new Slider(8, 72, this.getFontSize());
        fontSizeSlider.setBlockIncrement(1);
        fontSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> setFontSize(newVal.intValue()));
        nodes.add(new Label("Font Size:"));
        nodes.add(fontSizeSlider);

        // Create a checkbox to toggle bold font
        CheckBox boldCheckbox = new CheckBox("Bold");
        boldCheckbox.setSelected(this.isBold());
        boldCheckbox.setOnAction(event -> setBold(boldCheckbox.isSelected()));
        nodes.add(new Label("Bold:"));
        nodes.add(boldCheckbox);

        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(nodes);

        return infoBox;
    }

}
