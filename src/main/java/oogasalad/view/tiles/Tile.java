package oogasalad.view.tiles;

public interface Tile {
  public int getTileId();

  public double[] getPosition();

  public int[] getNext();

  public int[] getOnLand();

  public int[] getAfterTurn();
}
