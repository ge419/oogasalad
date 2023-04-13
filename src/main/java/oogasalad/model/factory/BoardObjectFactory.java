package oogasalad.model.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.util.ClassPathMatcher;

public class BoardObjectFactory {
  private final ClassPathMatcher pathFinder;

  public BoardObjectFactory() {
    this.pathFinder = new ClassPathMatcher();
  }

  public GameConstruct generate(String type, Map<String, Attribute> attributes)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String classPath = pathFinder.getKey(type);
    Class<?> dataClazz = Class.forName(classPath);
    Constructor<?> constructor = dataClazz.getConstructor();
    GameConstruct bConstruct = (GameConstruct) constructor.newInstance();

    return null;
  }

}
