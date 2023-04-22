package oogasalad.model.accesscontrol.authentication;

import com.google.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.accesscontrol.database.DatabaseAccessor;

public class FirebaseAuthHandler implements AuthenticationHandler {
  private boolean userLogInStatus = false;
  private String activeUser = null;
  private DatabaseAccessor db;
  @Inject
  public FirebaseAuthHandler(DatabaseAccessor db){
    this.db = db;
  }
  @Override
  public void login(String username, String password) {
    if (!db.isUserRegistered(username)){
      register( username,  password);
    }
    activeUser = username;
    setUserLogInStatus(true);
  }
  @Override
  public void logout() {
    setUserLogInStatus(false);
  }
  @Override
  public void register(String username, String password) {
    Map<String, Object> docData = new HashMap<>();
    docData.put("username",  username);
    docData.put("password", password);
    docData.put("games", Arrays.asList());

    db.modifyUser(docData);
  }
  @Override
  public boolean getUserLogInStatus() {
    return userLogInStatus;
  }
  @Override
  public String getActiveUser() {
    return activeUser;
  }
  private void setUserLogInStatus(boolean logInStatus) {
    userLogInStatus = logInStatus;
  }
}
