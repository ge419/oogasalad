package oogasalad.model.accesscontrol;

import com.google.inject.AbstractModule;
import oogasalad.model.accesscontrol.authentication.firebase.FirebaseAuthHandlerModule;
import oogasalad.model.accesscontrol.dao.firebase.FirebaseDaoModule;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessorModule;

/**
 * Guice module that binds Firebase implementation of DatabaseAccessor and AuthenticationHandler
 * interfaces in singleton scope.
 *
 * @author Chika Dueke-Eze
 */
public class AccessControlModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FirebaseAuthHandlerModule());
    install(new FirebaseAccessorModule());
    install(new FirebaseDaoModule());
  }
}
