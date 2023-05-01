package oogasalad.model.accesscontrol.dao.firebase;

import com.google.inject.AbstractModule;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;

/**
 * Guice module for that binds {@link GameDao} and {@link UserDao} to
 * their concrete classes—{@link FirebaseGameDao} and {@link FirebaseUserDao} respectively
 *
 * @author cgd19
 */
public class FirebaseDaoModule extends AbstractModule {
  @Override
  protected void configure(){
    bind(GameDao.class).to(FirebaseGameDao.class);
    bind(UserDao.class).to(FirebaseUserDao.class);
  }
}
