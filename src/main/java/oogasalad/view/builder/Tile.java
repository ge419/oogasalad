package oogasalad.view.builder;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Tile extends Rectangle {
    private String myType;
    Tile (double X, double Y, String type) {
        this.setFill(Color.WHITE);
        this.setStroke(Color.BLACK);
        this.setX(X);
        this.setY(Y);
        this.setHeight(5.0f);
        this.setWidth(5.0f);
        myType = type;
    }


}
