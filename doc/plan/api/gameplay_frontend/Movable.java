package gameplay_frontend;

/**
 * A class representing all frontend components that should be movable. This class defines the
 * properties and behavior of movable components in the game, such as its type, value, and effect.
 * Implementation details:
 * <p>
 * move(int x, int y) - This method moves the component a specified distance
 * from its current position. The x and y parameters represent the change in position in the
 * horizontal and vertical directions, respectively. This method might throw an InvalidMoveException
 * if the move is not allowed due to game rules or other constraints.
 * <p>
 * moveTo(int x, int y) - This method moves the component to a specific position on the screen. The
 * x and y parameters represent the absolute position of the component. This method might also throw
 * an InvalidMoveException if the move is not allowed.
 * <p>
 * animateMove(int x, int y, int duration) - This method animates the movement of the component from
 * its current position to a new position over a specified duration. The x and y parameters
 * represent the final position of the component, and the duration parameter represents the length
 * of time for the animation in milliseconds. This method might throw an InvalidMoveException if the
 * move is not allowed.
 */

public interface Movable {

  public void move(int x, int y) throws InvalidMoveException;

  public void moveTo(int x, int y) throws InvalidMoveException;

  public void animateMove(int x, int y, int duration) throws InvalidMoveException;
}
