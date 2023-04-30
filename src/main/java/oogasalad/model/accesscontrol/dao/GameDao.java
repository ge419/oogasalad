package oogasalad.model.accesscontrol.dao;

import java.util.List;
import java.util.Map;

public interface GameDao {
  Map<String, Object> getGameData(String gameID);
  String createGame(String username, Map<String, Object> game);
  void updateGame(String gameID, Map<String, Object> game);
  List<String> getAllGames();
  void postGameReview(String review, String gameID, String userID);
}
