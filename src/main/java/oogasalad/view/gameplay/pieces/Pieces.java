package oogasalad.view.gameplay.pieces;

import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Player;
import oogasalad.view.Renderable;

public class Pieces implements Renderable {

  private final List<Player> BPlayers;
  private PlayerPiece piece;

  public Pieces(List<Player> p) {
    this.BPlayers = p;
  }

  @Override
  public void render(BorderPane pane) {
    //TODO: add logic that decides how many pieces are parsed and with what images
    for (Player p : BPlayers) {
      piece = new PlayerPiece(p);
      pane.getChildren().add(piece);
    }
//    piece = new PlayerPiece("data/example/piece_1.png", "Bob");
//    piece.setId("Pieces");
//    pane.getChildren().add(piece);
  }

  public PlayerPiece getPiece() {
    return piece;
  }
}
