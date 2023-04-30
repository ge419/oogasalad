package oogasalad.view.tabexplorer;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.util.ResourceBundle;
import oogasalad.model.accesscontrol.AccessControlModule;
import oogasalad.view.tabexplorer.tabs.TabFactory;
import oogasalad.view.tabexplorer.tabs.settings.options.SettingsOptionsFactory;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

/**
 * This module defines the dependency injections for the tab explorer view.
 * It installs the {@link AccessControlModule} and the {@link TabFactory} and defines bindings for
 * controllers that are used by the tab explorer view.
 */
public class TabModule extends AbstractModule {
  private final ResourceBundle resourceBundle;

  public TabModule(ResourceBundle resourceBundle) {
    this.resourceBundle = resourceBundle;
  }
  @Override
  protected void configure() {
    bind(ResourceBundle.class).toInstance(resourceBundle);
    bind(UserPreferences.class).in(Scopes.SINGLETON);
    install(new AccessControlModule());
    install(new FactoryModuleBuilder().build(TabFactory.class));
    install(new FactoryModuleBuilder().build(SettingsOptionsFactory.class));
  }
}

