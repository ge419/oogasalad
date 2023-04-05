/**
 * Communicates with the frontend to choose an option via a given strategy.
 */
interface Chooser {

  /**
   * Randomly choose a value from the list.
   */
  T randomChoice<T>(List<T> options);

  /**
   * Have the player choose exactly one choice.
   * Options should implement toString.
   */
  T playerRadioChoice<T>(List<T> options);

  /**
   * Have the player choose between minSelections and maxSelections out of the given options.
   * Options should implement toString.
   * options.length() should be >= minSelections.
   */
  T playerCheckboxChoice<T>(List<T> options, int minSelections, int maxSelections);

  /**
   * Randomly returns a roll of a 6D die.
   * Specialization for frontend animation.
   */
  int random6DChoice();

  /**
   * Asks the player to select a tile.
   * Ensures the returned tile matches the given predicate.
   */
  Tile playerTileChoice(Predicate<Tile> predicate);
}