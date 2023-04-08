package oogasalad.view.builder;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oogasalad.view.Coordinate;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.File;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class PopupFormTest extends DukeApplicationTest {
    private PopupForm popupForm;
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private ResourceBundle resourceBundle;
    private Coordinate coordinate;
    @Override
    public void start(Stage stage){
        resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        PopupForm popupForm = new PopupForm<>(Coordinate.class, resourceBundle);
        coordinate = (Coordinate) popupForm.displayForm();
    }
    @Test
    void displayForm() {
        TextField arg0 = lookup("#arg0").query();
        writeInputTo(arg0, "0");
        TextField arg1 = lookup("#arg1").query();
        writeInputTo(arg1, "1");
        Button submit = lookup("#SubmitForm").query();
        clickOn(submit);
        assertEquals(0, coordinate.getXCoor());
        assertEquals(1, coordinate.getYCoor());
    }
}