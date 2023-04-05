package oogasalad.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PlayerDeserializer extends StdDeserializer {

  public PlayerDeserializer() {
    this(null);
  }

  public PlayerDeserializer(Class vc) {
    super(vc);
  }

  @Override
  public Constructable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    Player player = new Player();
    ObjectMapper mapper = new ObjectMapper();
    TypeFactory typeFactory = mapper.getTypeFactory();
    MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, BAttribute.class);

    //construct attribute map
    HashMap<String, BAttribute> attributes = mapper.readValue(Paths.get("ExampleSchema.json").toFile(), mapType);
    for (Map.Entry<?, ?> entry : attributes.entrySet()) {
      System.out.println(entry.getKey() + "=" + entry.getValue());
    }
    player.setAttributes(attributes);
    return player;
  }
}
