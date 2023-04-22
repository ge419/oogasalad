package oogasalad.view.tabexplorer;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.model.accesscontrol.AccessControlModule;
import oogasalad.view.tabexplorer.tabs.TabFactory;

/**
 * Guice module for the tab explorer view. It installs the {@link AccessControlModule} and the
 * {@link TabFactory} and defines bindings for controllers that are used by the tab explorer view.
 */
public class TabModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new AccessControlModule());
    install(new FactoryModuleBuilder().build(TabFactory.class));
    //todo add binding for controller
  }
}
