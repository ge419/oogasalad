package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.graphs.ImmutableGraph;

/**
 * A basic implementation of an Immutable game object.
 *
 * @author tmh85
 */
public class ImmutableGameHolder implements GameInterface {

  private final ImmutableBoardInfo myBoardInfo;
  private final ImmutableGraph myGraph;

  public ImmutableGameHolder(GameInterface game) {
    myBoardInfo = game.getBoardInfo();
    myGraph = game.getTileGraph();
  }

  @Override
  public ImmutableGraph getTileGraph() {
    return myGraph;
  }

  @Override
  public ImmutableBoardInfo getBoardInfo() {
    return myBoardInfo;
  }

  @Override
  public String getGameFilePath() {
    return null;
  }
}
