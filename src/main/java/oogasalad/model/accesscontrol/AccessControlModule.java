package oogasalad.model.accesscontrol;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.authentication.FirebaseAuthHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.dao.firebase.FirebaseGameDao;
import oogasalad.model.accesscontrol.dao.firebase.FirebaseUserDao;
import oogasalad.model.accesscontrol.database.FirebaseAccessor;

/**
 * Guice module that binds Firebase implementation of DatabaseAccessor and AuthenticationHandler
 * interfaces in singleton scope.
 *
 * @author Chika Dueke-Eze
 */
public class AccessControlModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(FirebaseAccessor.class).in(Scopes.SINGLETON);
    bind(AuthenticationHandler.class).to(FirebaseAuthHandler.class).in(Scopes.SINGLETON);
    bind(GameDao.class).to(FirebaseGameDao.class);
    bind(UserDao.class).to(FirebaseUserDao.class);

  }
}
