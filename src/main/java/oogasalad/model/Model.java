package oogasalad.model;

import java.io.IOException;


public class Model {
  public static void main(String[] args) throws IOException {
    //TODO: Check File Path and Debug Deeper Placed Files ex. /data
    BFactory factory = new BFactory();
    Constructable player = factory.generate("ExampleSchema.json");
    System.out.println(player.getAttributeValue("id"));
  }

}
