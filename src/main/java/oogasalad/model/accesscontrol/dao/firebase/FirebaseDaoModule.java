package oogasalad.model.accesscontrol.dao.firebase;

import com.google.inject.AbstractModule;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;

public class FirebaseDaoModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(GameDao.class).to(FirebaseGameDao.class);
    bind(UserDao.class).to(FirebaseUserDao.class);
  }
}
