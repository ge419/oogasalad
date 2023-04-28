package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
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

    public Coordinate getPosition() {
        return new Coordinate(this.getTranslateX(), this.getTranslateY());
    }

    public void setPosition(Coordinate coord) {
        this.setLayoutX(coord.getXCoor());
        this.setLayoutY(coord.getYCoor());
        this.getTransforms().add(new Rotate(coord.getAngle(), Rotate.Z_AXIS));
    }

    public Paint getColor() {
        return null;
    }

    public void setColor(Color color) {
    }

    @Override
    public Tile getTile() {
        return null;
    }

    @Override
    public void setSize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public Node asNode() {
        return (Node) this;
    }
}
