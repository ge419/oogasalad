package oogasalad.model.observers;

import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;

public interface GameObserver {

  default void updateOnPlayers(Players players) {}
  default void updateOnPieces(List<Piece> pieces){}
  default void updateOnPlayerRemoval(List<Player> players){}
  default void updateOnGameEnd(){};
}
