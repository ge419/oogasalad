package oogasalad.model.accesscontrol.database;

import java.util.List;
import java.util.Map;

public interface DatabaseAccessor {
  // todo: need to split this for more specific stuff
  //like have updateUserName, updateUserPicture,
  //update age etc
  void modifyUser(Map<String, Object> userData);

  boolean isUserRegistered(String userID);

  //todo should throw an exception is game with same name for user
  //already exists
  void createGame(String username, Map<String, Object> game);
  void editGame(String gameID, Map<String, Object> game);

  //TODO: hash out if game will be stored as string, so no need for Object
  // how will game be saved, dominic/jason mentioned upload/dwnld files..i'm
  // thinking cloud storage buckets?
  List<Map<String,Object>> getGamesForUser(String userID);

  // Social Center
  List<Map<String,Object>> GetAllGames(String userID);

  List<Map<String,Object>> getGameReviewS(String gameID);
  void postGameReview(String review, String gameID, String userID);

  //todo should throw an exception if game already exists for user
  void cloneGame(String gameID, String userID);
}
