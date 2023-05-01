package oogasalad.model.accesscontrol.dao;

import java.util.Map;

public interface UserDao {

  String getUserID(String userName);

  Map<String, Object> getUserData(String userID);

  boolean isUserRegistered(String username);

  String registerNewUser(String username, String password);

  void incrementNumberOfGamesPlayed(String userID);

  void setUserName(String userID, String newUsername);

  void setUserFullName(String userID, String newUserFullName);

  //todo should throw an exception if game already exists for user
  //or rather button should be disabled and display, "game cloned"
  void subscribeToGame(String userID, String gameID);

  void unsubscribeToGame(String userID, String gameID); // can only unclone if not creator

  void deleteGame(String gameID); // has to be creator to delete

  void updatePassword(String userID, String newPwd);

  void updateEmailAddress(String userID, String email);

  void updateUserPronouns(String userID, String pronouns);

  void updateAge(String userID, int age);

  void updatePreferredTheme(String userID, String preferredTheme);

  void updatedPreferredLanguage(String userID, String preferredLang);
}