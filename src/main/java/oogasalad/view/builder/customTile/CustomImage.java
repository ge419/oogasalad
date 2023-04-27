package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.model.attribute.ImageMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.StringMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomImage extends ImageView implements CustomElement {

    String imageName = "";
    Path destinationPath;
    File originalFile;
    double x; double y;
    int index = -1;

    public CustomImage(File file) {
        super(new Image(file.toURI().toString()));
        originalFile = file;
        this.setPreserveRatio(true);
    }

    public CustomImage(JsonObject jsonObject) {
        super(new Image(new File(jsonObject.get("filePath").getAsString()).toURI().toString()));
        this.imageName = jsonObject.get("name").getAsString();
        this.originalFile = new File(jsonObject.get("filePath").getAsString());
        this.setFitWidth(jsonObject.get("width").getAsDouble());
        this.setFitHeight(jsonObject.get("height").getAsDouble());
        this.x = jsonObject.get("x").getAsDouble();
        this.y = jsonObject.get("y").getAsDouble();
        this.index = jsonObject.get("index").getAsInt();

    }


    public void fitImage(double rightPaneWidth, double rightPaneHeight) {
        // Resize the image to fit within the bounds of the StackPane
        double maxWidth = rightPaneWidth - 20;
        double maxHeight = rightPaneHeight - 20;
        double width = this.getImage().getWidth();
        double height = this.getImage().getHeight();
        double scale = Math.min(maxWidth / width, maxHeight / height);
        this.setFitWidth(scale * width);
        this.setFitHeight(scale * height);
    }

    public VBox getInfo() {
        List<Node> nodes = new ArrayList<>();

        // Create a label for the image name field
        Label imageNameLabel = new Label("Image Name:");

        // Create a text field to edit the image name
        TextField imageNameField = new TextField(this.imageName);
        imageNameField.setOnAction(event -> setImageName(imageNameField.getText()));
        nodes.addAll(Arrays.asList(imageNameLabel, imageNameField));

        // Create a label for the size slider
        Label sizeLabel = new Label("Image Size:");

        // Create a slider to scale the image
        Slider sizeSlider = createSizeSlider();
        nodes.addAll(Arrays.asList(sizeLabel, sizeSlider));

        // Create buttons to send image to front or back
        Button toFrontButton = new Button("Send to Front");
        toFrontButton.setOnAction(event -> this.toFront());
        Button toBackButton = new Button("Send to Back");
        toBackButton.setOnAction(event -> this.toBack());
        nodes.addAll(Arrays.asList(toFrontButton, toBackButton));

        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(nodes);

        return infoBox;
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


    private Slider createSizeSlider() {
        Slider sizeSlider = new Slider(10, ((Pane) this.getParent()).getWidth(), this.getFitWidth());
        sizeSlider.setBlockIncrement(10);
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double scalingFactor = newVal.doubleValue() / oldVal.doubleValue();
            this.setFitWidth(scalingFactor * this.getFitWidth());
            this.setFitHeight(scalingFactor * this.getFitHeight());
        });
        return sizeSlider;
    }

    private void setImageName(String text) {
        this.imageName = text;
    }

    public JsonObject save(Path directory) throws IOException {
        this.destinationPath = directory;

        if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
        }
        saveImage(this.originalFile, directory);

        // Create JSON object and add properties
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "CustomImage");
        jsonObject.addProperty("x", this.getTranslateX());
        jsonObject.addProperty("y", this.getTranslateY());
        jsonObject.addProperty("name", this.imageName);
        jsonObject.addProperty("height", this.getFitHeight());
        jsonObject.addProperty("width", this.getFitWidth());
        jsonObject.addProperty("filePath", destinationPath.toString()+"/"+this.imageName);
        jsonObject.addProperty("index", this.getParent().getChildrenUnmodifiable().indexOf(this));

        return jsonObject;
    }




    private void saveImage(File originalFile, Path directoryPath) throws IOException {
        String saveName = this.imageName;
        saveName = resolveImageName(saveName);

        System.out.println("saveName = " + saveName);
        Path newFilePath = directoryPath.resolve(saveName);
        if (!Files.exists(newFilePath)) {
            Files.createFile(newFilePath); //Line Errors
        }

        Files.copy(originalFile.toPath(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private String resolveImageName(String saveName) {
        if (this.imageName == null) {
            this.imageName = this.originalFile.getName();
            System.out.println("this.originalFile.getName() = " + this.originalFile.getName());
        }
        this.imageName = this.imageName.endsWith(".png") ? this.imageName : this.imageName + ".png";
        return imageName;
    }

    @Override
    public Metadata getMetaData() {
        ImageMetadata metadata = new ImageMetadata(this.imageName);
        metadata.setDefaultValue(this.destinationPath.toString());
        Boolean named = (imageName == null);
        String temp = (named ? "this" : "the " + imageName);
        metadata.setEditable(named);
        metadata.setEditable(named);
        metadata.setDescription("The path to " + temp + " image");
        return metadata;
    }
}
