package oogasalad.model.accesscontrol.authentication.firebase;

import com.google.inject.Inject;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.UserDao;

public class FirebaseAuthHandler implements AuthenticationHandler {

  private boolean userLogInStatus = false;
  private String activeUserID = null;
  private String activeUserUsername = null;
  private final UserDao userDao;

  @Inject
  public FirebaseAuthHandler(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void login(String username, String password)
      throws ExecutionException, InterruptedException {
    activeUserUsername = username;
    activeUserID = userDao.getUserID(username);
    setUserLogInStatus(true);
  }

  @Override
  public void logout() {
    setUserLogInStatus(false);
  }

  @Override
  public void register(String username, String password)
      throws ExecutionException, InterruptedException {
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
