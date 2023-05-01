package oogasalad.model.accesscontrol.database.firebase;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Guice module for firebase accessor.
 *
 * @author cgd19
 */
public class FirebaseAccessorModule extends AbstractModule {
  @Override
  protected void configure(){
    bind(FirebaseAccessor.class).in(Scopes.SINGLETON);
  }
}
