package oogasalad.model.accesscontrol.dao;

import java.util.Map;

/**
 * Interface specifying contract for accessing a user construct object from a data storage medium.
 *
 * @author cgd19
 */

public interface UserDao {

  /**
   * Retrieves the unique user ID associated with the given username.
   * @param userName The username to retrieve the associated user ID for.
   * @return The user ID associated with the given username.
   */
  String getUserID(String userName);

  /**
   * Retrieves the user data for the user with the given ID.
   * @param userID The ID of the user to retrieve data for.
   * @return A map of user data associated with the given user ID.
   */
  Map<String, Object> getUserData(String userID);


  /**
   * Checks if a user with the given username is registered in the system.
   * @param username The username to check for registration.
   * @return True if the user is registered, false otherwise.
   */
  boolean isUserRegistered(String username);

  /**
   * Registers a new user in the system with the given username and password.
   * @param username The username of the new user to register.
   * @param password The password of the new user to register.
   * @return The ID of the newly registered user.
   */
  String registerNewUser(String username, String password);

  /**
   * crements the number of games played for the user with the given ID.
   * @param userID The ID of the user to increment the number of games played for.
   */
  void incrementNumberOfGamesPlayed(String userID);

  /**
   * Updates the username for the user with the given ID.
   * @param userID The ID of the user to update the username for.
   * @param newUsername The new username to set for the user.
   */
  void updateUserName(String userID, String newUsername);

  /**
   * Updates the full name for the user with the given ID.
   * @param userID The ID of the user to update the full name for.
   * @param newUserFullName The new full name to set for the user.
   */
  void updateUserFullName(String userID, String newUserFullName);

  /**
   * Subscribes the user with the given ID to the game with the given ID.
   * @param userID The ID of the user to subscribe to the game.
   * @param gameID The ID of the game to subscribe the user to.
   */
  void subscribeToGame(String userID, String gameID);

  /**
   * Unsubscribes the user with the given ID from the game with the given ID.
   * @param userID The ID of the user to unsubscribe from the game.
   * @param gameID The ID of the game to unsubscribe the user from.
   */
  void unsubscribeToGame(String userID, String gameID);

  /**
   * Deletes the game with the given ID.
   * @param gameID The ID of the game to delete.
   */
  void deleteGame(String gameID);

  /**
   * Updates the password for the user with the given ID.
   * @param userID The ID of the user to update the password for.
   * @param newPwd The new password to set for the user.
   */
  void updatePassword(String userID, String newPwd);

  /**
   * Updates the email address for the user with the given ID.
   * @param userID The ID of the user to update the email address for.
   * @param email The new email address to set for the user.
   */
  void updateEmailAddress(String userID, String email);

  /**
   * Updates the pronouns of the user with the given userID.
   * @param userID the ID of the user whose pronouns are being updated
   * @param pronouns the updated pronouns of the user
   */
  void updateUserPronouns(String userID, String pronouns);

  /**
   * Updates the age of the user with the given userID.
   * @param userID the ID of the user whose age is being updated
   * @param age the updated age of the user
   */
  void updateAge(String userID, int age);

  /**
   * Updates the preferred theme of the user with the given userID.
   * @param userID the ID of the user whose preferred theme is being updated
   * @param preferredTheme the updated preferred theme of the user
   */
  void updatePreferredTheme(String userID, String preferredTheme);

  /**
   * Updates the preferred language of the user with the given userID.
   * @param userID the ID of the user whose preferred language is being updated
   * @param preferredLang the updated preferred language of the user
   */
  void updatedPreferredLanguage(String userID, String preferredLang);

  /**
   * Deletes all users.
   */
  void deleteAllUsers();
}