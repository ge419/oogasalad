package oogasalad.model.observers;

import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Players;

public interface GameObserver {

  void updateOnPlayers(Players players);
  void updateOnPieces(List<Piece> pieces);
}
