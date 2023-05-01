package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.nio.file.Path;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.util.SaveManager;
import oogasalad.view.builder.BuilderUtility;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import oogasalad.view.builder.BuilderView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A strategy used by the popup form to display a form element when
 * an image path is required from the user via user input. This consists
 * of one button that opens a file dialog when clicked.
 * Example Usage:
 * VBox form = new VBox();
 * ImageParameterStrategy strategy = new ImageParameterStrategy(myImageAttribute, myImageMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick
 */
public class ImageParameterStrategy implements ParameterStrategy, BuilderUtility {
    private final ImageAttribute attr;
    private final ImageMetadata meta;
    private String imagePath;
    private List<String> validExtensions = List.of("jpg", "png");
    private SaveManager manager;
    private static final Logger LOG = LogManager.getLogger(ImageParameterStrategy.class);
    /**
     * Creates an instance of ImageParameterStrategy
     * Can be used to display form data to a user for an image,
     * validate the input, save to an attribute, and access
     * the corresponding ImageAttribute and ImageMetadata
     * @param attr ImageAttribute
     * @param meta ImageMetadata
     */
    @Inject
    public ImageParameterStrategy(
            @Assisted Attribute attr,
            @Assisted Metadata meta,
            SaveManager manager) {
        this.attr = ImageAttribute.from(attr);
        this.meta = (ImageMetadata) ImageMetadata.from(meta);
        this.manager = manager;
    }
    /**
     * Returns a JavaFX form element for a image attribute
     * @param resourceBundle
     * @param form parent pane of element
     * @return HBox containing a button for selecting an image
     */
    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<File> file = fileLoad(resourceBundle, "UploadFileTitle");
                try {
                    imagePath = file.get().getPath().toString();
                } catch (Exception e) {
                    LOG.warn("User uploaded null or non-image file");
                }
            }
        });
        return makeHBox(String.format("%sImageInput", name), textLabel, element);
    }
    /**
     * Saves input to instance's ImageAttribute
     */
    @Override
    public void saveInput() {
        try {
            String filename = manager.saveAsset(Path.of(getFieldValue()));
            attr.setValue(filename);
        } catch (IOException e) {
            LOG.error("Unable to save user asset with SaveManager");
        }
    }
    /**
     * Uses metadata to validate user input
     * @return boolean (true means input is valid)
     */
    @Override
    public boolean isInputValid() {
        return meta.isValidValue(getFieldValue());
    }
    /**
     * Gets ImageMetadata
     * @return ImageMetadata
     */
    @Override
    public Metadata getMetadata() {
        return meta;
    }
    /**
     * Gets ImageAttribute
     * @return ImageAttribute
     */
    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private String getFieldValue() {
        return imagePath;
    }
}
