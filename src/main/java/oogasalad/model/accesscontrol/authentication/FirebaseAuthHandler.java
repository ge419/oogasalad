package oogasalad.model.accesscontrol.authentication;

import com.google.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.database.DatabaseAccessor;

public class FirebaseAuthHandler implements AuthenticationHandler {

  private boolean userLogInStatus = false;
  private String activeUserID = null;
  private String activeUserUsername = null;
  private UserDao userDao;

  @Inject
  public FirebaseAuthHandler(UserDao userDao){
    this.userDao = userDao;
  }

  @Override
  public void login(String username, String password)
      throws ExecutionException, InterruptedException {
    if (!userDao.isUserRegistered(username)){
      register( username,  password);
    }
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
    activeUserID = userDao.registerNewUser(username,password);
  }

  @Override
  public boolean getUserLogInStatus() {
    return userLogInStatus;
  }

  @Override
  public String getActiveUserID() {
    return activeUserID;
  }

  @Override
  public String getActiveUserName() {
    return activeUserUsername;
  }
  private void setUserLogInStatus(boolean logInStatus) {
    userLogInStatus = logInStatus;
  }
}