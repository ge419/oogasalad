package oogasalad.view.builder.customTile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class CustomTileMaker extends Application {

    private String defaultTitle = "CustomTileMaker";

    private Stage stage;

    private LeftPane leftPane;

    private StackPane rightPane;

    private TextField windowSize = new TextField("400");
    private final int LEFT_PANE_WIDTH = 100;
    private int RIGHT_PANE_STARTING_WIDTH = 300;
    private int STARTING_HEIGHT = 300;






    @Override
    public void start(Stage PrimaryStage) {
        // Create StackPane for the right pane
        rightPane = new StackPane();

        stage = PrimaryStage;
        // Create UI elements for the left and right panes
        TextField aspectRatioField = new TextField(String.valueOf(STARTING_HEIGHT/RIGHT_PANE_STARTING_WIDTH));
        aspectRatioField.setMaxWidth(.8*LEFT_PANE_WIDTH);
        aspectRatioField.setOnAction(e -> resizeWindow(Double.parseDouble(aspectRatioField.getText())));



        // Create leftPane
        leftPane = new LeftPane(rightPane);
        leftPane.getChildren().addAll(aspectRatioField);
        leftPane.setMinWidth(LEFT_PANE_WIDTH);
        leftPane.setMaxWidth(LEFT_PANE_WIDTH);


        // Create SplitPane and add left and right panes
        SplitPane splitPane = new SplitPane();
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

        // Create scene and add SplitPane
        Scene scene = new Scene(splitPane, LEFT_PANE_WIDTH+RIGHT_PANE_STARTING_WIDTH, STARTING_HEIGHT);

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();

        // Set listener for changes to window size
        stage.widthProperty().addListener((obs, oldVal, newVal) -> maintainSideBar(aspectRatioField));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> maintainSideBar(aspectRatioField));
    }


    private void resizeWindow(Double aspectRatio) {
        try {
            // Parse the user's input as an integer
            double height = stage.getHeight();
            System.out.println("aspectRatio = " + aspectRatio);
            double newWidth = LEFT_PANE_WIDTH + height*aspectRatio;
            System.out.println("newWidth = " + newWidth);
            // Resize the right side of the window to the new width
            stage.setWidth(LEFT_PANE_WIDTH + height*aspectRatio);
        } catch (NumberFormatException e) {
            // Ignore invalid input
        }
    }

    private void maintainSideBar(TextField aspectRatioField) {
        double height = stage.getHeight();
        double width = stage.getWidth() - LEFT_PANE_WIDTH;
        double aspectRatio = width / height;
        Platform.runLater(() -> aspectRatioField.setText(String.format("%.2f", aspectRatio)));
    }


    public void launch() {
        launch(new String[]{});
    }

    public static void main(String[] args) {
        launch(args);
    }

}

