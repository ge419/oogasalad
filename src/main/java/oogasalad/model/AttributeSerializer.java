package oogasalad.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class AttributeSerializer extends StdSerializer<Constructable> {

  public AttributeSerializer(Class t) {
    super(t);
  }

  /**
   *
   * @param constructable
   * @param jsonGenerator
   * @param serializerProvider
   * @throws IOException
   */
  //TODO: parameter Constructable should be changed according to the Record/specific class passed from frontend builder
  //TODO: using ObjectMapper requires standard getter/setter methods --> any way to avoid this?
  @Override
  public void serialize(Constructable constructable, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(new File("data/PlayerTest.json"), constructable);
    String playerAsString = mapper.writeValueAsString(constructable);
    System.out.println(playerAsString);
  }
}
