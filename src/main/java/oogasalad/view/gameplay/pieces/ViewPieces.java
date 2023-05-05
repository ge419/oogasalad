package oogasalad.view.gameplay.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Piece;
import oogasalad.view.Renderable;

public class ViewPieces implements Renderable {

  private final List<PlayerPiece> pieceList;

  public ViewPieces(List<Piece> p) {
    this.pieceList = new ArrayList<>();
    for (Piece piece : p) {
      pieceList.add(new PlayerPiece(piece));
    }
  }

  @Override
  public void render(BorderPane pane) {
    for (PlayerPiece p : pieceList) {
      p.setId("Piece");
      pane.getChildren().add(p);
    }
  }

  public PlayerPiece getPiece() {
    return pieceList.get(0);
  }

  public PlayerPiece getViewPieceByBPiece(Piece piece) {
    for (PlayerPiece playerPiece : pieceList) {
      if (piece.equals(playerPiece.getModelPiece())) {
        return playerPiece;
      }
    }
    throw new IllegalArgumentException("No such piece");
  }
}
