package oogasalad.view.builder.gameholder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;

public class GameHolderDirector {
  //TODO: make the parameters as list?
  //TODO: use reflection to initialize GameHolderBuilder in GameHolder

  BBoard board;
  Optional<List<Player>> players;
  // list of cards

  //TODO: parameter should be map? list?
  public GameHolderDirector(List<?> gameObjectList) {
//    this.board =
  }

  public void addPlayers(GameHolderBuilder builder, List<Player> playerList) {
    builder.setPlayers(playerList);
  }

  public void addBoard(GameHolderBuilder builder, BBoard board) {
    builder.setBoard(board);
  }

//  public void addCards(GameHolderBuilder builder, List<T> cards) {
//    builder.setCards();
//  }

}
