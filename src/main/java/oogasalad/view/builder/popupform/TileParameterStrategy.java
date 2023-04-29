package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.attribute.TileMetadata;
import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.events.TileEvent;

import javax.inject.Inject;
import java.util.ResourceBundle;

/**
 * A strategy used by the popup form to display a form element when
 * a Tile is required from the user via user input. This consists
 * of one button that creates an event listener when clicked. The
 * event listener allows the user to select a tile
 * Example Usage:
 * VBox form = new VBox();
 * TileParameterStrategy strategy = new TileParameterStrategy(myTileAttribute, myTileMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick
 */
public class TileParameterStrategy implements ParameterStrategy, BuilderUtility {
    private static final String ROOT_ID = "#BoardPane";
    private final TileAttribute attr;
    private final TileMetadata meta;
    private Tile tile;
    private Pane root;
    private Button element;
    /**
     * Creates an instance of TileParameterStrategy
     * Can be used to display form data to a user for a tile,
     * validate the input, save to an attribute, and access
     * the corresponding TileAttribute and TileMetadata
     * @param attr TileAttribute
     * @param meta TileMetadata
     */
    @Inject
    public TileParameterStrategy(@Assisted Attribute attr, @Assisted Metadata meta) {
        this.attr = TileAttribute.from(attr);
        this.meta = TileMetadata.from(meta);
    }
    private void addHandlerToRoot(ResourceBundle resourceBundle) {
        root.addEventHandler(TileEvent.SELECT_TILE, event -> {
            tile = event.getTile();
            element.setText(String.format(String.format("%s: %s", resourceBundle.getString("Selected"), tile.getId())));
        });
    }
    /**
     * Returns a JavaFX form element for a tile attribute
     * @param resourceBundle
     * @param form parent pane of element
     * @return HBox containing a button
     */
    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        if (this.attr.getId() == null) {
            element = new Button(resourceBundle.getString("SelectTile"));
        } else {
            element = new Button(String.format("%s: %s", resourceBundle.getString("Selected"), this.attr.getId()));
        }

        element.setOnAction(e -> {
            Scene scene = form.getScene();
            this.root = (Pane) scene.lookup(ROOT_ID);
            addHandlerToRoot(resourceBundle);
        });
        return makeHBox(String.format("%sTileInput", name), textLabel, element);
    }
    /**
     * Saves input to instance's TileAttribute
     */
    @Override
    public void saveInput() {
        attr.setId(getFieldValue());
    }
    /**
     * Uses metadata to validate user input
     * @return boolean (true means input is valid)
     */
    @Override
    public boolean isInputValid() {
        return meta.isValidTileId(getFieldValue());
    }
    /**
     * Gets TileMetadata
     * @return TileMetadata
     */
    @Override
    public Metadata getMetadata() {
        return meta;
    }
    /**
     * Gets TileAttribute
     * @return TileAttribute
     */
    @Override
    public Attribute getAttribute() {
        return attr;
    }
    private String getFieldValue() {
        return tile.getId();
    }
}
