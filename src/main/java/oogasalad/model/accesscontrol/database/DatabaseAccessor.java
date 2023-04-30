package oogasalad.model.accesscontrol.database;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DatabaseAccessor {
  // todo: need to split this for more specific stuff
  //like have updateUserName, updateUserPicture,
  //update age etc
  void modifyUser(Map<String, Object> userData);

  // UserDao
  void setUserName(String userID, String newUsername);
  void setUserFullName(String userID, String newUserFullName);

  void getGames(String userID);

  //todo should throw an exception if game already exists for user
  //or rather button should be disabled and display, "game cloned"
  void cloneGame(String userID, String gameID);
  void unCloneGame(String userID, String gameID);
  void deleteGame(String userID, String gameID); // has to be user to delete

  void setPassword(String userID, String newPwd);
  Date getDateJoined(String userID);
  String getEmailAddress(String userID);
  void setEmailAddress(String userID, String email);

  String getAge(String userID);
  String setAge(String userID, int age);

  boolean isUserRegistered(String userID);

  //todo should throw an exception is game with same name for user
  //already exists
  void createGame(String username, Map<String, Object> game);
  void updateGame(String gameID, Map<String, Object> game);

  //TODO: hash out if game will be stored as string, so no need for Object
  // how will game be saved, dominic/jason mentioned upload/dwnld files..i'm
  // thinking cloud storage buckets?
  List<Map<String,Object>> getGamesForUser(String userID);

  // Social Center
  List<Map<String,Object>> GetAllGames(String userID);

  List<Map<String,Object>> getGameReviewS(String gameID);
  void postGameReview(String review, String gameID, String userID);


}
