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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomImage extends ImageView {

    String imageName;
    Path imagePath;

    public CustomImage(Image image) {
        super(image);
        this.setPreserveRatio(true);
    }

    public void placeInRightBox(double rightPaneWidth, double rightPaneHeight) {
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
        Slider sizeSlider = new Slider(10, ((Pane) this.getParent()).getWidth(), this.getFitWidth());
        sizeSlider.setBlockIncrement(10);
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double scalingFactor = newVal.doubleValue() / oldVal.doubleValue();
            this.setFitWidth(scalingFactor * this.getFitWidth());
            this.setFitHeight(scalingFactor * this.getFitHeight());
        });
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

    private void setImageName(String text) {
        this.imageName = text;
    }

    public JsonObject save(Path directory) {
        // Create JSON object and add properties
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "CustomImage");
        jsonObject.addProperty("x", this.getLayoutX());
        jsonObject.addProperty("y", this.getLayoutY());
        if (this.imageName == null) {
            this.imageName = this.getImage().getUrl();
        }
        jsonObject.addProperty("name", this.imageName);
        if (this.imagePath != null) {
            jsonObject.addProperty("imagePath", this.imagePath.toString());
            Path imageSavePath = directory.resolve(this.imagePath.getFileName().toString());
            try {
                Files.copy(this.imagePath, imageSavePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("Error saving image file: " + e.getMessage());
            }
        }
        return jsonObject;
    }
}
