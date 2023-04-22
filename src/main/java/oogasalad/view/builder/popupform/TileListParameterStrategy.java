package oogasalad.view.builder.popupform;

import com.google.common.eventbus.EventBus;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.events.TileEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TileListParameterStrategy implements ParameterStrategy, BuilderUtility {
    private static final String ROOT_ID = "#BoardPane";
    private final TileListAttribute attr;
    private final TileListMetadata meta;
    private List<Tile> tiles = new ArrayList<>();
    private Pane root;
    private Button element;

    @Inject
    public TileListParameterStrategy(@Assisted Attribute attr, @Assisted Metadata meta) {
        this.attr = TileListAttribute.from(attr);
        this.meta = TileListMetadata.from(meta);

        if (this.attr.getTileIds().isEmpty()) {
            element = new Button("Select Tiles");
        } else {
            element = new Button(String.format("Selected: %s", this.attr.getTileIds().stream().collect(Collectors.joining(","))));
        }

        element.setOnAction(e -> {
            Scene scene = element.getScene();
            root = (Pane) scene.lookup(ROOT_ID);
            addHandlerToRoot();
        });
    }
    private void addHandlerToRoot() {
        root.addEventHandler(TileEvent.SELECT_TILE, event -> {
            tiles.add(event.getTile());
            element.setText(String.format("Selected: %s", tiles.stream().map(Tile::getId).collect(Collectors.joining(","))));
        });
    }

    @Override
    public Node renderInput(ResourceBundle resourceBundle) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        return makeHBox(String.format("%sTileListInput", name), textLabel, element);
    }

    @Override
    public void saveInput() {
        attr.setTileIds(getFieldValue());
    }

    @Override
    public boolean isInputValid() {
        return meta.isValidTileIds(getFieldValue());
    }

    @Override
    public Metadata getMetadata() {
        return meta;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }
    private List<String> getFieldValue() {
        return tiles.stream().map(Tile::getId).collect(Collectors.toList());
    }
}
