package oogasalad.model.builder;

import java.io.IOException;
import oogasalad.model.constructable.BBoard;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;


public interface BBuilderAPI {

  /**
   * Saves the custom-built game into a directory of JSON files and images
   * @param holder    Data passed from the frontend builder
   * @param board     Data stored in the backend
   * @throws IOException  Serialization
   */
  void save(ImmutableGameHolder holder, BBoard board) throws IOException;

  /**
   * Loads a pre-built game from a directory of JSON files and images
   * @param filePath    File path of the directory of the game
   * @throws IOException  Deserialization
   */
  void load(String filePath) throws IOException;
}
