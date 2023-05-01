package oogasalad.view.gameplay.pieces;

import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.util.Util;
import oogasalad.view.Coordinate;

/**
 * Represents a game piece that can be controlled by a player. Inherits from {@link GamePiece}.
 *
 * <p>This class allows players to move a piece to a specified tile, and holds a reference to the
 * corresponding model piece.</p>
 *
 * <p>Assumes that the model piece passed to the constructor is not null. Assumes that the tile
 * property of the model piece will not change while the player piece is in use.</p>
 *
 * <p>Dependencies: {@link GamePiece}, {@link Piece}, {@link Tile}, {@link Util},
 * {@link Coordinate}.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Piece modelPiece = new Piece(...);
 * PlayerPiece playerPiece = new PlayerPiece(modelPiece);
 * playerPiece.moveToTile(someTile);
 * }</pre>
 *
 * <p>Other details: This class does not handle invalid moves or out-of-bounds coordinates.</p>
 *
 * @author ajo24, dcm67, wj61, jy320
 */
public class PlayerPiece extends GamePiece {

  private final Piece modelPiece;

  /**
   * Constructs a new {@code PlayerPiece} object with the given model piece.
   *
   * @param BPiece the model piece to associate with this player piece
   */
  public PlayerPiece(Piece BPiece) {
    super(BPiece.getImage());
    this.modelPiece = BPiece;
    Util.initializeAndListen(modelPiece.tileProperty(),
        optionalTile -> optionalTile.ifPresent(this::moveToTile));
  }

  /**
   * Moves this player piece to the specified tile, and updates the model piece to reflect its new
   * location.
   *
   * @param tile the tile to move to
   * @throws NullPointerException if the tile is null
   */
  public void moveToTile(Tile tile) {
    modelPiece.setTile(tile);
    System.out.println(tile.getCoordinate().toString());
    moveDirectly(tile.getCoordinate(), this);
  }

  /**
   * Returns the model piece associated with this player piece.
   *
   * @return the model piece associated with this player piece
   */
  public Piece getModelPiece() {
    return modelPiece;
  }

  /**
   * Moves this player piece along a path defined by an array of coordinates.
   *
   * @param coorArray an array of coordinates defining the path to follow
   * @throws UnsupportedOperationException if called (this method is not yet implemented)
   */
  @Override
  public void move(Coordinate[] coorArray) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }
}