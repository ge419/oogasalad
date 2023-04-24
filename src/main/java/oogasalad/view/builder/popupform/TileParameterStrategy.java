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

public class TileParameterStrategy implements ParameterStrategy, BuilderUtility {
    private static final String ROOT_ID = "#BoardPane";
    private final TileAttribute attr;
    private final TileMetadata meta;
    private Tile tile;
    private Pane root;
    private Button element;

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

    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form) {
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

    @Override
    public void saveInput() {
        attr.setId(getFieldValue());
    }

    @Override
    public boolean isInputValid() {
        return meta.isValidTileId(getFieldValue());
    }

    @Override
    public Metadata getMetadata() {
        return meta;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }
    private String getFieldValue() {
        return tile.getId();
    }
}
