package oogasalad.model.accesscontrol.authentication.firebase;

import com.google.inject.Inject;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.UserDao;

/**
 * The FirebaseAuthHandler class implements the {@link AuthenticationHandler} interface and offers a
 * range of methods for managing user authentication through Firebase. It tracks the login status of
 * a user, as well as the active user's ID and username, enabling the secure management of user
 * data.
 *
 * @author cgd19
 */
public class FirebaseAuthHandler implements AuthenticationHandler {

  private final UserDao userDao;
  private boolean userLogInStatus = false;
  private String activeUserID = null;
  private String activeUserUsername = null;

  @Inject
  public FirebaseAuthHandler(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void login(String username, String password) {
    activeUserUsername = username;
    activeUserID = userDao.getUserID(username);
    setUserLogInStatus(true);
  }

  @Override
  public void logout() {
    setUserLogInStatus(false);
  }

  @Override
  public void register(String username, String password) {
    activeUserID = userDao.registerNewUser(username, password);
  }

  @Override
  public boolean getUserLogInStatus() {
    return userLogInStatus;
  }

  private void setUserLogInStatus(boolean logInStatus) {
    userLogInStatus = logInStatus;
  }

  @Override
  public String getActiveUserID() {
    return activeUserID;
  }

  @Override
  public String getActiveUserName() {
    return activeUserUsername;
  }
}
