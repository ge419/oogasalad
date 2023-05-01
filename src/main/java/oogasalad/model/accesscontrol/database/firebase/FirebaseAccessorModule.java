package oogasalad.model.accesscontrol.database.firebase;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class FirebaseAccessorModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(FirebaseAccessor.class).in(Scopes.SINGLETON);
  }
}
