package oogasalad.model.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.constructable.Constructable;
import oogasalad.util.ClassPathMatcher;

public class ConstructableFactory {
  private final ClassPathMatcher pathFinder;

  public ConstructableFactory() {
    this.pathFinder = new ClassPathMatcher();
  }

  public Constructable generate(String type, Map<String, Attribute> attributes)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String classPath = pathFinder.getKey(type);
    Class<?> dataClazz = Class.forName(classPath);
    Constructor<?> defaultConstructor = dataClazz.getConstructor();
    Constructable bConstruct = (Constructable) defaultConstructor.newInstance();
    bConstruct.setAttributes(attributes);

    return bConstruct;
  }

}
