package oogasalad.model;

import java.io.File;
import java.io.IOException;


public class Model {
  public static void main(String[] args) throws IOException {
//    BFactory factory = new BFactory();
//    File playerfile = new File("data/example/ExamplePlayer.json");
//    Constructable player = factory.generate(playerfile);
//
//    File tilefile = new File("data/example/ExampleTile.json");
//    Constructable tile = factory.generate(tilefile);
//
//    System.out.println(player.getAttributeValue("self"));
//    System.out.println(player.getAttributeValue("id"));
//    System.out.println(player.getAttributeValue("name"));
//    System.out.println(player.getAttributeValue("score"));
////    player.updateAttributeValue("score", new BValue(1000));
//    System.out.println(player.getAttributeValue("score"));
//
//    System.out.println(tile.getAttributeValue("self"));
//    System.out.println(tile.getAttributeValue("xPosition"));
//    System.out.println(tile.getAttributeValue("yPosition"));
    BMetaData metaData = new BMetaData("name", BType.STRING);
    BValue name = new BValue(metaData,"Bob");
    System.out.println(name.getValue(String.class));
  }





}
