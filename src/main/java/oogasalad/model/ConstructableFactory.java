package oogasalad.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.AttributeDeserializer;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.util.ClassPathMatcher;

public class ConstructableFactory {
  private static final String CLASS_IDENTIFIER = "self";
  private final ClassPathMatcher pathFinder;
  private final AttributeDeserializer deserializer;

  public ConstructableFactory() {
    this.deserializer = new AttributeDeserializer();
    this.pathFinder = new ClassPathMatcher();
  }

  public Constructable generate(File file) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    TypeFactory typeFactory = mapper.getTypeFactory();
    MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, Attribute.class);
    module.addDeserializer(HashMap.class, new AttributeDeserializer());
    mapper.registerModule(module);
    Map<String, Attribute> attributes = mapper.readValue(file, mapType);

    try {
      String classId = StringAttribute.from(attributes.get(CLASS_IDENTIFIER)).getValue();
      String classPath = pathFinder.getKey(classId);
      Class<?> dataClazz = Class.forName(classPath);
      Constructor<?> defaultConstructor = dataClazz.getConstructor();
      Constructable bConstruct = (Constructable) defaultConstructor.newInstance();
      bConstruct.setAttributes(attributes);
      return bConstruct;

    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
             IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }

  }
}
