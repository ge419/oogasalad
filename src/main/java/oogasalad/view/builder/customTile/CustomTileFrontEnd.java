package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.tiles.ViewTile;

public class CustomTileFrontEnd extends StackPane implements ViewTile {

    public CustomTileFrontEnd(){
        super();
    }

    @Override
    public String getTileId() {
        return null;
    }

    @Override
    public Coordinate getPosition() {
        return new Coordinate(this.getTranslateX(), this.getTranslateY());
    }

    @Override
    public void setPosition(Coordinate coord) {

    }

    @Override
    public Paint getColor() {
        return null;
    }

    @Override
    public void setColor(Color color) {
    }

    @Override
    public Tile getTile() {
        return null;
    }

    @Override
    public Node asNode() {
        return (Node) this;
    }
}
