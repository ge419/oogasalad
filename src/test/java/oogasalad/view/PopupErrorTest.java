package oogasalad.view;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class PopupErrorTest extends DukeApplicationTest {
    private final String DEFAULT_ERROR_TYPE = "BadSizeError";
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private ResourceBundle resourceBundle;
    private visualization.PopupError myPopupError;
    @Override
    public void start (Stage stage) {
        resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        myPopupError = new visualization.PopupError(resourceBundle, DEFAULT_ERROR_TYPE);
    }
    @Test
    void testPopup() {
        Text errorMsg = lookup(String.format("#%s", DEFAULT_ERROR_TYPE)).query();

        Button btn = lookup("Ok").query();

        assertEquals(resourceBundle.getString(DEFAULT_ERROR_TYPE), errorMsg.getText());
        assertEquals(resourceBundle.getString("Ok"), btn.getText());

        clickOn(btn);
    }
}