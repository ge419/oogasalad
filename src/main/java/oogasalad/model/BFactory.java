package oogasalad.model;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import oogasalad.util.ClassPathMatcher;

public class BFactory {
  private final ClassPathMatcher pathFinder;
  private final AttributeDeserializer deserializer;

  public BFactory() {
    this.deserializer = new AttributeDeserializer();
    this.pathFinder = new ClassPathMatcher();
  }

  public Constructable generate(String fileName) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module =
        new SimpleModule("deserializer", new Version(1, 0, 0, null, null, null));
    TypeFactory typeFactory = mapper.getTypeFactory();
    MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, BAttribute.class);
    module.addDeserializer(HashMap.class, new AttributeDeserializer());
    mapper.registerModule(module);
    Path path = Paths.get(fileName);
    Map<String, BAttribute> attributes = mapper.readValue(path.toFile(), mapType);

    try {
      String classPath = pathFinder.getKey(attributes.get("self").getValue());
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
