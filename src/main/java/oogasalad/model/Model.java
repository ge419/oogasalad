package oogasalad.model;

import java.io.IOException;


public class Model {
  public static void main(String[] args) throws IOException {
    //TODO: Check File Path and Debug Deeper Placed Files ex. /data
    BFactory factory = new BFactory();
    Constructable b = factory.generate("ExampleSchema.json");
    System.out.println(b.getAttributeValue("id"));
  }

}
