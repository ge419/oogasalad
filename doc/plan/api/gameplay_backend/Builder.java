public interface Builder {

  /**
   * Saves the game specifications to JSON file.
   *
   * usage: called to save current version of the custom built game specifications to a JSON file,
   * which is readable by the Engine to launch the game.
   */
  void saveFile();

  /**
   * Loads the game specifications from JSON file.
   *
   * usage: called to extract data from existing JSON file of game specifications,
   * and create and write data classes which will be compiled into a Builder UI.
   */
  void loadFile();
}