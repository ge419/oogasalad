package visualization;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.view.builder.BuilderUtility;

import java.util.ResourceBundle;

/**
 * Displays a window with an error message taken from the properties file.
 * User can exit window by pressing Ok after reading the message
 * @author Jason Fitzpatrick
 */
public class PopupError implements BuilderUtility{
    private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";
    private static final String ERROR_ICON_PATH = "/view/ErrorIcon.png";
    private static final double SCENE_WIDTH = 250;
    private static final double SCENE_HEIGHT = 150;
    private static final double ICON_SIZE = 50;

    /**
     * Displays a window with an error message, and a button to close the window when read.
     * @param resourceBundle ResourceBundle used to get properties for new elements
     * @param errorType String representation of the type of error used to lookup the proper message
     */
    public PopupError (ResourceBundle resourceBundle, String errorType) {
        Stage stage = new Stage();
        stage.setTitle(resourceBundle.getString("ErrorWindowTitle"));

        ImageView errorIcon = initializeImage(ERROR_ICON_PATH);

        Text msg = (Text) makeWrappedText(errorType, resourceBundle, SCENE_WIDTH);
        msg.setTextAlignment(TextAlignment.CENTER);

        Button btn = (Button) makeButton("Ok", resourceBundle, e -> stage.close());

        VBox vBox = (VBox) makeVBox("ErrorContainer", errorIcon, msg, btn);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
    private ImageView initializeImage(String path) {
        ImageView imageView = new ImageView();
        Image img = new Image(path);
        imageView.setImage(img);
        imageView.setFitWidth(ICON_SIZE);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        return imageView;
    }
}
