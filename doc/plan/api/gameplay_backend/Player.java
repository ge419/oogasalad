public interface Player {
  /**
   * Returns the name of the player.
   *
   * usage: can be called to retrieve the name of the player at any point during the game,
   * such as when displaying the player's information on the game board, or when checking the winner of the game.
   */
  String getName();

  /**
   * Sets the name of the player.
   *
   * @param name the new name of the player
   *
   * usage: can be called to update the name of the player,
   * such as when the player wants to change their name during the game.
   */
  void setName(String name);

  /**
   * Returns the current score of the player.
   *
   * usage: can be called to retrieve the current score of the player,
   * such as when displaying the player's score on the game board, or when calculating the winner of the game.
   */
  int getScore();

  /**
   * Sets the score of the player.
   *
   * @param score the new score of the player
   *
   * usage: can be called to set the initial score of the player or to reset the score to a specific value,
   * such as when initializing a new game or resetting the current game.
   */
  void setScore(int score);

  /**
   * Adds the specified number of points to the player's score.
   *
   * @param points the number of points to add
   *
   * usage: can be called when the player earns points, such as when they successfully complete a task or level in the game.
   * The specified number of points will be added to the player's current score.
   */
  void addPoints(int points);

  /**
   * Subtracts the specified number of points from the player's score.
   *
   * @param points the number of points to subtract
   *
   * usage: can be called when the player loses points, such as when they make a mistake or fail to complete a task in the game.
   * The specified number of points will be subtracted from the player's current score.
   */
  void subtractPoints(int points);
}