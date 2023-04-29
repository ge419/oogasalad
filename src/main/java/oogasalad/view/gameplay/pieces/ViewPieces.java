package oogasalad.view.gameplay.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Piece;
import oogasalad.view.Renderable;

public class ViewPieces implements Renderable {

  private final List<PlayerPiece> pieceList;

  public ViewPieces(Optional<List<Piece>> p) {
    this.pieceList = new ArrayList<>();
      for (Piece piece : p.get()) {
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

  public List<PlayerPiece> getPieceList() {
    return pieceList;
  }

  public PlayerPiece getPiece() {
    return pieceList.get(0);
  }
}
