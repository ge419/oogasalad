package oogasalad.view.builder.popupform;

import com.google.common.eventbus.EventBus;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.attribute.TileMetadata;
import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.events.TileEvent;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class TileParameterStrategy implements ParameterStrategy, BuilderUtility {

    private final TileAttribute attr;
    private final TileMetadata meta;
    private Tile tile;
    private Pane root;
    private Button element;

    @Inject
    public TileParameterStrategy(@Assisted Attribute attr, @Assisted Metadata meta, Pane root) {
        this.attr = TileAttribute.from(attr);
        this.meta = TileMetadata.from(meta);
        this.root = root;

        element = new Button("Select Tile");
        element.setOnAction(e -> {
            addHandlerToRoot();
        });
    }
    private void addHandlerToRoot() {
        root.addEventHandler(TileEvent.CLICK_TILE, event -> {
            tile = event.getTile();
        });
    }

    @Override
    public Node renderInput(ResourceBundle resourceBundle) {
        String name = meta.getName();
        Node textLabel = new Text(name + " (Tile)");
        return makeHBox(String.format("%sTileInput", name), textLabel, element);
    }

    @Override
    public void saveInput() {
        attr.setId(tile.getId());
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
