package oogasalad.model.accesscontrol.authentication;

/**
 * The AuthenticationHandler interface provides methods to handle user authentication operations
 * such as login, logout and registration. It also provides methods to get the login status of the
 * user, and the active user.
 *
 * @author Chika Dueke-Eze
 */

public interface AuthenticationHandler {

  /**
   * Logs in the user with the provided username and password.
   *
   * @param username The username of the user to log in.
   * @param password The password of the user to log in
   */
  void login(String username, String password);

  /**
   * Logs out the currently logged-in user.
   */
  void logout();

  /**
   * Registers a new user with the provided username and password.
   *
   * @param username The username of the new user to register.
   * @param password The password of the new user to register.
   */
  void register(String username, String password);

  /**
   * Returns the login status of the current user.
   *
   * @return true if the user is logged in, false otherwise.
   */
  boolean getUserLogInStatus();

  /**
   * Returns the username of the currently active user.
   *
   * @return The username of the currently active user, or null if no user is currently logged in.
   */
  String getActiveUserName();

  /**
   * Returns the userID of the currently active user.
   *
   * @return The userID of the currently active user, or null if no user is currently logged in.
   */
  String getActiveUserID();
}
