package oogasalad.model.accesscontrol.dao;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface UserDao {

  String getUserID(String userName) throws ExecutionException, InterruptedException;

  Map<String, Object> getUserData(String userID);

  boolean isUserRegistered(String userID);

  String registerNewUser(String username, String password)
      throws ExecutionException, InterruptedException;

  void incrementNumberOfGamesPlayed(String userID);

  void setUserName(String userID, String newUsername);

  void setUserFullName(String userID, String newUserFullName);

  //todo should throw an exception if game already exists for user
  //or rather button should be disabled and display, "game cloned"
  void cloneGame(String userID, String gameID);

  void unCloneGame(String userID, String gameID); // can only unclone if not creator

  void deleteGame(String userID, String gameID); // has to be creator to delete

  void updatePassword(String userID, String newPwd);

  void updateEmailAddress(String userID, String email);

  void updateUserPronouns(String userID, String pronouns);

  void updateAge(String userID, int age);

  void updatePreferredTheme(String userID, String preferredTheme);

  void updatedPreferredLanguage(String userID, String preferredLang);
}