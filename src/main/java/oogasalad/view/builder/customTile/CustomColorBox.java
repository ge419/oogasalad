package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.model.attribute.Metadata;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomColorBox extends Rectangle implements CustomElement {

    private String name = "";
    private String defaultColor;
    private double x; private double y;

    private boolean editable = false;
    private int index = -1; // it is -1 unless it is loaded from JSON

    private final String UNDERSPECIFIED_COLOR = "#FF0000";
    private final double UNDERSPECIFIED_OPACITY = .5;
    private final double UNDERSPECIFIED_LENGTH = 50;


    public CustomColorBox() {
        super();
        Color a = Color.web(UNDERSPECIFIED_COLOR);
        Color b = Color.web(UNDERSPECIFIED_COLOR, UNDERSPECIFIED_OPACITY);
        this.setFill(Color.web(UNDERSPECIFIED_COLOR, UNDERSPECIFIED_OPACITY));
        this.setWidth(UNDERSPECIFIED_LENGTH);
        this.setHeight(UNDERSPECIFIED_LENGTH);
    }



    @Override
    public JsonObject save(Path folderPath) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("color", this.getFill().toString());
        jsonObject.addProperty("opacity", this.getOpacity());
        jsonObject.addProperty("index", this.getParent().getChildrenUnmodifiable().indexOf(this));
        jsonObject.addProperty("translatex", this.getTranslateX());
        jsonObject.addProperty("translatey", this.getTranslateY());
        jsonObject.addProperty("width", this.getWidth());
        jsonObject.addProperty("height", this.getHeight());
        jsonObject.addProperty("editable", this.editable);

        return jsonObject;
    }
    @Override
    public VBox getInfo() {
        ArrayList colorBoxSpecificElements = new ArrayList();
        Label widthLabel = new Label("Width");
        colorBoxSpecificElements.add(widthLabel);
        colorBoxSpecificElements.add(createWidthSlider());
        Label heightLabel = new Label("Height");
        colorBoxSpecificElements.add(heightLabel);
        colorBoxSpecificElements.add(createHeightSlider());
        return CustomElementHelper.makeVbox(this, colorBoxSpecificElements);
    }

    @Override
    public void setLocation() {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String text) {
        name = text;

    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean selected) {
        this.editable = selected;
    }

    private Slider createWidthSlider() {
        Slider widthSlider = new Slider(10, ((Pane) this.getParent()).getWidth(), this.getWidth()*1.1); //1.1 so users don't have to perfectly align
        widthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.setWidth(newVal.doubleValue());
        });
        return widthSlider;
    }

    private Slider createHeightSlider() {
        Slider heightSlider = new Slider(10, ((Pane) this.getParent()).getHeight(), this.getHeight()*1.1); //1.1 so users don't have to perfectly align
        heightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.setHeight(newVal.doubleValue());
        });
        return heightSlider;
    }

    @Override
    public Metadata getMetaData() {
        //TODO MAKE COLORBOX METADATA
        return null;
    }
}
