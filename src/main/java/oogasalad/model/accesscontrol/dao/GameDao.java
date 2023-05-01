package oogasalad.model.accesscontrol.dao;

import java.util.List;
import java.util.Map;

/**
 * Interface specifying contract for accessing a game construct object from a data storage medium.
 *
 * @author cgd19
 */
public interface GameDao {

  /**
   * Retrieves game data from the data storage  given a game ID.
   *
   * @param gameID the ID of the game to retrieve data for
   * @return a Map of the game's data
   */
  Map<String, Object> getGameData(String gameID);

  /**
   * Creates a new game in the data storage and returns the game's ID.
   *
   * @param username the username of the game creator
   * @return the ID of the newly created game
   */
  String createGame(String username);

  /**
   * Updates the data for a game in the data storage given the game's ID and the new data.
   *
   * @param gameID the ID of the game to update
   * @param game   a Map of the new game data
   */
  void updateGame(String gameID, Map<String, Object> game);

  /**
   * Retrieves a list of all game IDs in the data storage .
   *
   * @return a List of all game IDs in the data storage.
   */
  List<String> getAllGames();

  /**
   * Posts a review for a game in the data storage given the review, game ID, and user ID.
   *
   * @param review the review to post
   * @param gameID the ID of the game to post the review for
   * @param userID the ID of the user posting the review
   */
  void postGameReview(String review, String gameID, String userID);

  /**
   * Deletes all users.
   */
  void deleteAllGames();
}
