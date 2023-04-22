package oogasalad.model.accesscontrol;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.authentication.FirebaseAuthHandler;
import oogasalad.model.accesscontrol.database.DatabaseAccessor;
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
    bind(DatabaseAccessor.class).to(FirebaseAccessor.class).in(Scopes.SINGLETON);
    bind(AuthenticationHandler.class).to(FirebaseAuthHandler.class).in(Scopes.SINGLETON);
  }
}
