package oogasalad.model;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Model {
  public static void main(String[] args) throws IOException {
    String filename="ExampleSchema.json";
    Path pathToFile = Paths.get(filename);
    System.out.println(pathToFile.toAbsolutePath());

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module =
        new SimpleModule("PlayerDeserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Player.class, new PlayerDeserializer());
    mapper.registerModule(module);
    Player player = mapper.readValue(Paths.get("ExampleSchema.json").toFile(), Player.class);
    System.out.println(player.toString());
    System.out.println(player.getAttributeValue("name"));
  }

}
