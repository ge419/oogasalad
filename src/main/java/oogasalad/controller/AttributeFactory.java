package oogasalad.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.engine.rules.EditableRule;
import oogasalad.util.ClassPathMatcher;

public class AttributeFactory {

  private final ClassPathMatcher pathFinder;

  public AttributeFactory() {
    this.pathFinder = new ClassPathMatcher();
  }

  public Attribute generate(String type, String key, Object value)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String classPath = pathFinder.getKey(type);
    Class<?> dataClazz = Class.forName(classPath);
    Constructor<?> constructor = dataClazz.getConstructor(String.class, value.getClass());
    Attribute rule = (Attribute) constructor.newInstance(key, value);
    return rule;
  }
}