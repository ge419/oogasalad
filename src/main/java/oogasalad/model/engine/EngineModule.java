package oogasalad.model.engine;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.net.URL;
import java.util.ResourceBundle;
import oogasalad.model.engine.actions.ActionFactory;

/**
 * Guice module for the {@link Engine}.
 *
 * @author Dominic Martinez
 */
public class EngineModule extends AbstractModule {

  private static final String DEFAULT_RESOURCE_PACKAGE = "engine.";
  private final ResourceBundle resourceBundle;

  public EngineModule(String lang) {
    this.resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + lang);
  }

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(ActionFactory.class));
    bind(ResourceBundle.class)
        .annotatedWith(EngineResourceBundle.class)
        .toInstance(resourceBundle);
    bind(ActionQueue.class).to(SimpleActionQueue.class);
    bind(Engine.class).to(SimpleEngine.class);
    bind(EventHandlerManager.class).to(SimpleEventHandlerManager.class);
  }
}
