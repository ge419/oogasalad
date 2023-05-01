package oogasalad.controller;

/**
 * Stores information about the game Currently stores width and height of the game board
 *
 * @author Changmin Shin
 */
public class GameInfo {

  private double width;
  private double height;

  /**
   * Getter method for board width
   *
   * @return Width as double
   */
  public double getWidth() {
    return width;
  }

  /**
   * Setter method for board width
   *
   * @param width Width of the board
   * @return GameInfo object with updated board width
   */
  public GameInfo setWidth(double width) {
    this.width = width;
    return this;
  }

  /**
   * Getter method for board height
   *
   * @return Height as double
   */
  public double getHeight() {
    return height;
  }

  /**
   * Setter method for board height
   *
   * @param height Height of the board
   * @return GameInfo object with updated board width
   */
  public GameInfo setHeight(double height) {
    this.height = height;
    return this;
  }
}
