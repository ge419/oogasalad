package oogasalad.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.util.ClassPathMatcher;

public class RuleFactory {

  private final ClassPathMatcher pathFinder;

  public RuleFactory() {
    this.pathFinder = new ClassPathMatcher();
  }

  public AbstractGameConstruct generate(String type, Object value)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String classPath = pathFinder.getKey(type);
    Class<?> dataClazz = Class.forName(classPath);
    Constructor<?> constructor = dataClazz.getConstructor(String.class, value.getClass());
    AbstractGameConstruct rule = (AbstractGameConstruct) constructor.newInstance(type, value);
    return rule;
  }
}