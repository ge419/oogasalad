package oogasalad.view.gameplay.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Piece;
import oogasalad.view.Renderable;

public class Pieces implements Renderable {

  private final List<PlayerPiece> pieceList;

  public Pieces(List<Piece> p) {
    this.pieceList = new ArrayList<>();
    for (Piece piece : p) {
      pieceList.add(new PlayerPiece(piece));
    }
  }

  @Override
  public void render(BorderPane pane) {
    //TODO: add logic that decides how many pieces are parsed and with what images
    for (PlayerPiece p : pieceList) {
      pane.getChildren().add(p);
    }
//    piece = new PlayerPiece("data/example/piece_1.png", "Bob");
//    piece.setId("Pieces");
//    pane.getChildren().add(piece);
  }

  public PlayerPiece getPiece() {
    return pieceList.get(0);
  }
}
