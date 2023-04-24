package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class LeftPane extends VBox {
    private final StackPane rightPane;

    private Node currentClickedInfo;

    public LeftPane(StackPane rightPane) {
        this.rightPane = rightPane;

        // Create button to add an image
        Button addImageButton = new Button("Add Image");
        addImageButton.setOnAction(e -> addImage());

        Button addTextButton = new Button("Add Text");
        addTextButton.setOnAction(e -> addText());

        // Create fields for image properties
        Label fileNameLabel = new Label();
        // Add image property fields to VBox
        getChildren().addAll(addImageButton, addTextButton, fileNameLabel);

        // Listen for image selection changes

        rightPane.setOnMouseClicked(event -> {
            if (event.getTarget() == rightPane) {
                // User clicked on empty space, remove any info boxes
                getChildren().removeAll(currentClickedInfo);
            }
        });
    }

    private void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

        // Create an ImageView from the image file
        CustomImage newImage = new CustomImage(new Image(selectedFile.toURI().toString()));

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

    private class Delta {
        double x, y;
    }




}
