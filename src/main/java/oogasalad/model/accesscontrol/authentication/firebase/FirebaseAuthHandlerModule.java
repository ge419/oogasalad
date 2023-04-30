package oogasalad.model.accesscontrol.authentication.firebase;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
public class FirebaseAuthHandlerModule extends AbstractModule {
  @Override
  protected void configure(){
    bind(AuthenticationHandler.class).to(FirebaseAuthHandler.class).in(Scopes.SINGLETON);
  }
}
