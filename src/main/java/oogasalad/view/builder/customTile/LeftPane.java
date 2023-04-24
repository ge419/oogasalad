package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class LeftPane extends VBox {
    private final StackPane rightPane;
    private final CustomTileMaker tileMaker;

    private Node currentClickedInfo;

    public LeftPane(CustomTileMaker tileMaker, StackPane rightPane) {
        this.rightPane = rightPane;
        this.tileMaker = tileMaker;

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            Path savePath = Paths.get("src/main/resources/customObjects");
            try {
                tileMaker.save(savePath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button renameButton = new Button("Name Custom Object");
        renameButton.setOnAction(e -> nameObject());

        Button addImageButton = new Button("Add Image");
        addImageButton.setOnAction(e -> addImage());

        Button addTextButton = new Button("Add Text");
        addTextButton.setOnAction(e -> addText());

        // Create fields for image properties
        Label fileNameLabel = new Label();
        // Add image property fields to VBox
        getChildren().addAll(fileNameLabel, saveButton, addImageButton, addTextButton, renameButton);

        // Listen for image selection changes

        rightPane.setOnMouseClicked(event -> {
            if (event.getTarget() == rightPane) {
                // User clicked on empty space, remove any info boxes
                getChildren().removeAll(currentClickedInfo);
            }
        });
    }

    private void nameObject() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name CustomTile");
        dialog.setHeaderText("Enter a new name for the CustomTile:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.setTitle(name);
        });
    }

    private void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

        // Create an ImageView from the image file
        CustomImage newImage = new CustomImage(selectedFile);

        newImage.placeInRightBox(rightPane.getWidth(), rightPane.getHeight());

        newImage.setOnMouseClicked(event -> {
            VBox info = ((CustomImage) newImage).getInfo();
            if (info != currentClickedInfo) {
                getChildren().remove(currentClickedInfo);
                getChildren().add(info);
                currentClickedInfo = info;
            }
        });


        makeDraggable(newImage);
        rightPane.getChildren().add(newImage);
    }

    private void addText() {
        CustomText newText = new CustomText("Default Text", "Enter Text Here", 14, false);
        getChildren().add(newText);

        newText.setOnMouseClicked(event -> {
            VBox info = ((CustomText) newText).getInfo();
            if (info != currentClickedInfo) {
                getChildren().remove(currentClickedInfo);
                getChildren().add(info);
                currentClickedInfo = info;
            }
        });



        rightPane.getChildren().add(newText);
    }

    private void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = node.getTranslateX() - mouseEvent.getSceneX();
            dragDelta.y = node.getTranslateY() - mouseEvent.getSceneY();
        });
        node.setOnMouseDragged(mouseEvent -> {
            node.setTranslateX(mouseEvent.getSceneX() + dragDelta.x);
            node.setTranslateY(mouseEvent.getSceneY() + dragDelta.y);
        });
    }

    private void save(Node node) {

    }

    private class Delta {
        double x, y;
    }




}
