package oogasalad.view.tabexplorer;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.model.accesscontrol.AccessControlModule;
import oogasalad.view.tabexplorer.tabs.TabFactory;

/**
 * Guice module for the tab explorer view, which installs the {@link AccessControlModule} and
 * {@link TabFactory}, and defines controller bindings.
 */
public class TabModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new AccessControlModule());
    install(new FactoryModuleBuilder().build(TabFactory.class));
    //todo add binding for controller
  }
}
