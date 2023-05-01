package oogasalad.model.accesscontrol.authentication.firebase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import oogasalad.Main;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.dao.firebase.FirebaseDaoModule;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessorModule;
import oogasalad.model.accesscontrol.database.firebase.ServiceCredPath;
import oogasalad.model.exception.InvalidDatabaseExecutionException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirebaseAuthHandlerTest {

  private UserDao userDao;
  private  FirebaseAuthHandler authHandler;

  public FirebaseAuthHandlerTest() {
    init();

  }

  private void init(){
    Injector injector = Guice.createInjector(
        new FirebaseAuthHandlerModule(),
        new FirebaseAccessorModule(),
        new FirebaseDaoModule(),
        binder -> binder.bind(String.class).annotatedWith(ServiceCredPath.class)
            .toInstance(Main.TEST_SERVICE_ACCOUNT_CRED_PATH));

    authHandler = injector.getInstance(FirebaseAuthHandler.class);
    userDao = injector.getInstance(UserDao.class);
  }




  @AfterEach
  void tearDown() {
    userDao.deleteAllUsers();
  }

  @Test
  void loginCorrectly() {
    String username = "testUser";
    String password = "testPassword";

    authHandler.register(username, password);
    authHandler.login(username, password);
    assertTrue(authHandler.getUserLogInStatus());
    assertEquals(username, authHandler.getActiveUserName());
    assertEquals(userDao.getUserID(username), authHandler.getActiveUserID());
  }
}