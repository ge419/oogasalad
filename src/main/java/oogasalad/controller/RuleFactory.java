package oogasalad.controller;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.prompt.AIPrompter;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.util.ClassPathMatcher;
import oogasalad.view.tabexplorer.userpreferences.Languages;

public class RuleFactory {

  private final ClassPathMatcher pathFinder;

  public RuleFactory() {
    this.pathFinder = new ClassPathMatcher();
  }

  public AbstractGameConstruct generate(String type, SchemaDatabase db)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String classPath = pathFinder.getKey(type);
    Class<?> dataClazz = Class.forName(classPath);
    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        new AttributeModule(),
        new EngineModule(Languages.ENGLISH.getLocaleStr()),
        binder -> binder.bind(Prompter.class).toInstance(new AIPrompter())
    );
    //
//    Constructor<?> constructor = dataClazz.getConstructor(String.class, value.getClass());
////    AbstractGameConstruct rule = (AbstractGameConstruct) constructor.newInstance(type, value);
    return (AbstractGameConstruct) injector.getInstance(dataClazz);

  }
}