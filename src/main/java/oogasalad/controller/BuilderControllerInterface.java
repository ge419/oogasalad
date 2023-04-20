package oogasalad.controller;

import java.io.IOException;
import oogasalad.model.constructable.BBoard;
import oogasalad.view.builder.gameholder.GameHolder;

/**
 * Temporary mediator between frontend and backend builders
 * Will be integrated into general Controller class
 *
 * @author Changmin Shin
 */
public interface BuilderControllerInterface {

  /**
   * Calls BBuilder save method
   * @param holder    Data passed from the frontend builder
   * @param board     Data stored in the backend
   * @throws IOException  Serialization
   */
  void save(GameHolder holder) throws IOException;

  /**
   *
   */
  void load();

}
