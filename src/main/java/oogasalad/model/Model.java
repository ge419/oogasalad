package oogasalad.model;

import java.io.File;
import java.io.IOException;


public class Model {
  public static void main(String[] args) throws IOException {
    BFactory factory = new BFactory();
    File file = new File("data/example/ExampleSchema.json");
    Constructable b = factory.generate(file);
    System.out.println(b.getAttributeValue("self"));
    System.out.println(b.getAttributeValue("id"));
    System.out.println(b.getAttributeValue("name"));
    System.out.println(b.getAttributeValue("score"));
  }

}
