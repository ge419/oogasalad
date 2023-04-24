package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

public class LeftPane extends VBox {
    private final StackPane rightPane;

    private Node currentClickedInfo;

    public LeftPane(CustomTileMaker tileMaker, StackPane rightPane) {
        this.rightPane = rightPane;


        Button renameButton = new Button("Name Custom Object");
        renameButton.setOnAction(e -> nameObject());

        Button addImageButton = new Button("Add Image");
        addImageButton.setOnAction(e -> addImage());

        Button addTextButton = new Button("Add Text");
        addTextButton.setOnAction(e -> addText());

        // Create fields for image properties
        Label fileNameLabel = new Label();
        // Add image property fields to VBox
        getChildren().addAll(fileNameLabel, addImageButton, addTextButton, renameButton);

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
        CustomImage newImage = getCustomImage();
        newImage.fitImage(rightPane.getWidth(), rightPane.getHeight());
        placeInPane(newImage);
    }

    void placeInPane(CustomObject newObject) {
        Node newNode = (Node) newObject;
        newNode.setOnMouseClicked(event -> {
            VBox info = newObject.getInfo();
            if (info != currentClickedInfo) {
                getChildren().remove(currentClickedInfo);
                getChildren().add(info);
                currentClickedInfo = info;
            }
        });
        makeDraggable(newNode);
        int index = newObject.getIndex();
        if (index != -1){
            rightPane.getChildren().add(index, newNode);
        }
        else{
            rightPane.getChildren().add(newNode);
        }

    }



    @NotNull
    private CustomImage getCustomImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

        // Create an ImageView from the image file
        CustomImage newImage = new CustomImage(selectedFile);
        return newImage;
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
