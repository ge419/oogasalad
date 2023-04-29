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
    for (PlayerPiece p : pieceList) {
      pane.getChildren().add(p);
    }
  }

  public List<PlayerPiece> getPieceList() {
    return pieceList;
  }

  public PlayerPiece getPiece() {
    return pieceList.get(0);
  }
}
