package oogasalad.view.builder.customTile;

import javafx.application.Application;
import javafx.geometry.Orientation;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import oogasalad.view.builder.BuilderUtility;

import java.io.File;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomObjectBuilder extends Application implements BuilderUtility {
    private final String DEFAULT_TITLE = "CustomTileMaker";
    private final String STYLE_PATH =  "/customTiles/customObjectBuilder.css";
    private Stage stage;
    private LeftPane leftPane;
    private CustomObject rightPane;
    private ResourceBundle languageBundle;
    private static final int LEFT_PANE_WIDTH = 300;
    private int RIGHT_PANE_STARTING_WIDTH = 800;
    private int STARTING_HEIGHT = 800;



    @Override
    public void start(Stage PrimaryStage) {
        start(PrimaryStage, "en-US");
    }

    public void start(Stage PrimaryStage, String defaultLanguage) {
//        stage = PrimaryStage;
        stage = new Stage();
        this.languageBundle = ResourceBundle.getBundle("customTiles." + defaultLanguage);

        rightPane = new CustomObject(() -> leftPane.swapCurrentClicked(rightPane.currentClickedInfo, rightPane.newCurrentClickedInfo));
        leftPane = new LeftPane();

        // Create SplitPane and add left and right panes
        SplitPane splitPane = new SplitPane();
        formatBuilderSplitPane(splitPane);

        // Create scene and add SplitPane
        Scene scene = new Scene(splitPane, LEFT_PANE_WIDTH+RIGHT_PANE_STARTING_WIDTH, STARTING_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLE_PATH)).toExternalForm());


        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();
        stage.setTitle(DEFAULT_TITLE);

        // Set listener for changes to window size
        stage.widthProperty().addListener((obs, oldVal, newVal) -> maintainSideBar());

    }

    private void formatBuilderSplitPane(SplitPane splitPane) {
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.getItems().addAll(leftPane, rightPane);

        // Set initial position of the divider
        splitPane.setDividerPositions(0.3);

        // Set listener for changes to divider position
        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            leftPane.setPrefWidth(LEFT_PANE_WIDTH);
            double rightWidth = splitPane.getWidth() - LEFT_PANE_WIDTH;
            rightPane.setPrefWidth(rightWidth);
        });
    }




    private void maintainSideBar() {
        double height = stage.getHeight();
        double width = stage.getWidth() - LEFT_PANE_WIDTH;
    }

    public static int getLeftPaneWidth(){
        return LEFT_PANE_WIDTH;
    }




    public void launch() {
        launch(new String[]{});
    }

    public static void main(String[] args) {
        launch(args);
    }


    //remove inner class?
    private class LeftPane extends VBox {
        private LeftPane() {
            this.setMinWidth(LEFT_PANE_WIDTH);
            this.setMaxWidth(LEFT_PANE_WIDTH);

            AddButtons();

            // Listen for image selection changes
            rightPane.setOnMouseClicked(event -> {
                if (event.getTarget() == rightPane) {
                    getChildren().removeAll(rightPane.currentClickedInfo);
                }
            });
        }

        private void AddButtons() {
            Node renameButton = makeButton("renameButton",languageBundle, e -> rightPane.nameObject());
            Node saveButton = makeButton("saveButton",languageBundle, e -> rightPane.saveLauncher());
            Node loadButton = makeButton("loadButton",languageBundle, e -> rightPane.loadJson());

            Node addImageButton = makeButton("addImageButton",languageBundle, e -> addImage());
            Node addTextButton = makeButton("addTextButton",languageBundle, e -> addText());
            Node addColorButton  = makeButton("addColorButton",languageBundle, e -> addColorBox());

            this.getChildren().addAll(renameButton, saveButton, loadButton, addTextButton, addImageButton, addColorButton);
        }


        private void addImage() {
            CustomImage newImage = getImageFromFile();
            if (newImage != null){
                newImage.fitImage(rightPane.getWidth(), rightPane.getHeight());
                rightPane.placeElm(newImage);
            }
        }

        private CustomImage getImageFromFile() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(languageBundle.getString("selectImageFile.label"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg","*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

            if (selectedFile != null) {
                return new CustomImage(selectedFile);
                // rest of the code here
            } else {
                System.out.println("Error: selected file is null.");
            }
            return null;
        }

        private void addText() {
            CustomText newText = new CustomText(languageBundle.getString("defaultText").toString());
            rightPane.placeElm(newText);
        }

        private void addColorBox() {
            CustomColorBox newBox = new CustomColorBox();
            rightPane.placeElm(newBox);
            this.setTranslateX(0);
            this.setTranslateY(0);

        }

        private void swapCurrentClicked(VBox oldClickedInfo, VBox newClickedInfo){
            leftPane.getChildren().remove(oldClickedInfo);
            leftPane.getChildren().add(newClickedInfo);
        }

    }


}

