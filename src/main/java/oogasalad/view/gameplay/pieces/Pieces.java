package oogasalad.view.gameplay.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.view.Renderable;

public class Pieces implements Renderable {

  @Override
  public void render(BorderPane pane) {
    //TODO: add logic that decides how many pieces are parsed and with what images
    pane.getChildren().add(new PlayerPiece("data/example/piece_1.png", "Bob"));
  }
}
