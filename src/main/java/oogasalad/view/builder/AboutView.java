package oogasalad.view.builder;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p>Basic JavaFX class that displays the about window for our BuilderView.</p>
 * <p>Heavily inspired by previous SLogo code-- shoutout to Rodrigo!</p>
 *
 * @author tmh85
 */
public class AboutView {
  private static final String BOI_PATH = "boi.jpg";
  private static final double BOI_SIZE = 300;
  public AboutView(ResourceBundle language, String stylesheetPath){
    HBox entireBox = new HBox();
    VBox aboutTextBox = new VBox();
    aboutTextBox.getChildren().add(new Label(language.getString("TeamName")));
    aboutTextBox.getChildren().add(new Label(language.getString("TeamMembers")));
    ImageView image = new ImageView(new Image(BOI_PATH));
    image.setFitHeight(BOI_SIZE);
    image.setFitWidth(BOI_SIZE);
    aboutTextBox.setId("AboutText");
    entireBox.getChildren().addAll(image, aboutTextBox);
    entireBox.setId("About");
    Scene scene = new Scene(entireBox);
    scene.getStylesheets().add(stylesheetPath);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.showAndWait();
  }
}
