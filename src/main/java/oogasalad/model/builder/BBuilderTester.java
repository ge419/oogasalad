package oogasalad.model.builder;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.gameholder.GameHolder;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public class BBuilderTester {

  public static void main(String[] args) {
    BBuilder builder = new BBuilder();
    GameHolder gameHolder = new GameHolder();
//    gameHolder.setBoardInfo();
    ImmutableGameHolder holder = new ImmutableGameHolder(new GameHolder());

    List<Tile> tiles = new ArrayList<>();
//    tiles.add(new Tile())
    BBoard board = new BBoard(tiles, List.of());

//    builder.save();

  }

}
