package oogasalad.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class AttributeDeserializer extends StdDeserializer {

  public AttributeDeserializer() {
    this(null);
  }

  public AttributeDeserializer(Class vc) {//TODO: Check File Path and Debug Deeper Placed Files ex. /data
    super(vc);
  }

  @Override
  public HashMap<String, BAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    TypeFactory typeFactory = mapper.getTypeFactory();
    MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, BAttribute.class);
    return mapper.readValue(Paths.get("ExampleSchema.json").toFile(), mapType);
  }

}
