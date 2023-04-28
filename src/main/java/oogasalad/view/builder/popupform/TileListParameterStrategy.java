package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.events.TileEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * A strategy used by the popup form to display a form element when
 * a Tile list is required from the user via user input. This consists
 * of one button that creates an event listener when clicked. The
 * event listener allows the user to select tiles to be added to a
 * list of tiles
 * Example Usage:
 * VBox form = new VBox();
 * TileListParameterStrategy strategy = new TileListParameterStrategy(myTileListAttribute, myTileListMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick
 */
public class TileListParameterStrategy implements ParameterStrategy, BuilderUtility {
    private static final String ROOT_ID = "#BoardPane";
    private final TileListAttribute attr;
    private final TileListMetadata meta;
    private List<Tile> tiles = new ArrayList<>();
    private Pane root;
    private Button element;
    /**
     * Creates an instance of TileListParameterStrategy
     * Can be used to display form data to a user for a list of tiles,
     * validate the input, save to an attribute, and access
     * the corresponding TileListAttribute and TileListMetadata
     * @param attr TileListAttribute
     * @param meta TileListMetadata
     */
    @Inject
    public TileListParameterStrategy(@Assisted Attribute attr, @Assisted Metadata meta) {
        this.attr = TileListAttribute.from(attr);
        this.meta = TileListMetadata.from(meta);
    }
    private void addHandlerToRoot(ResourceBundle resourceBundle) {
        root.addEventHandler(TileEvent.SELECT_TILE, event -> {
            tiles.add(event.getTile());
            element.setText(String.format("%s: %s", resourceBundle.getString("Selected"), tiles.stream().map(Tile::getId).collect(Collectors.joining(","))));
        });
    }
    /**
     * Returns a JavaFX form element for a tile list attribute
     * @param resourceBundle
     * @param form parent pane of element
     * @return HBox containing a button
     */
    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        if (this.attr.getTileIds().isEmpty()) {
            element = new Button(resourceBundle.getString("SelectTiles"));
        } else {
            element = new Button(String.format("%s: %s", resourceBundle.getString("Selected"), this.attr.getTileIds().stream().collect(Collectors.joining(","))));
        }

        element.setOnAction(e -> {
            Scene scene = form.getScene();
            root = (Pane) scene.lookup(ROOT_ID);
            addHandlerToRoot(resourceBundle);
        });
        return makeHBox(String.format("%sTileListInput", name), textLabel, element);
    }
    /**
     * Saves input to instance's TileListAttribute
     */
    @Override
    public void saveInput() {
        attr.setTileIds(getFieldValue());
    }
    /**
     * Uses metadata to validate user input
     * @return boolean (true means input is valid)
     */
    @Override
    public boolean isInputValid() {
        return meta.isValidTileIds(getFieldValue());
    }
    /**
     * Gets TileListMetadata
     * @return TileListMetadata
     */
    @Override
    public Metadata getMetadata() {
        return meta;
    }
    /**
     * Gets TileListAttribute
     * @return TileListAttribute
     */
    @Override
    public Attribute getAttribute() {
        return attr;
    }
    private List<String> getFieldValue() {
        return tiles.stream().map(Tile::getId).collect(Collectors.toList());
    }
}
