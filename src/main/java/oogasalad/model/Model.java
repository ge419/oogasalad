package oogasalad.model;

import java.io.File;
import java.io.IOException;


public class Model {
  public static void main(String[] args) throws IOException {
    BFactory factory = new BFactory();
    File file = new File("data/example/ExamplePlayer.json");
    Constructable b = factory.generate(file);
    System.out.println(b.getAttributeValue("self"));
    System.out.println(b.getAttributeValue("id"));
    System.out.println(b.getAttributeValue("name"));
    System.out.println(b.getAttributeValue("score"));
    b.updateAttributeValue("score", new BValue(1000));
    System.out.println(b.getAttributeValue("score"));
  }

}
