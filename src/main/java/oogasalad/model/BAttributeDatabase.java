package oogasalad.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This utility class hold all attributes for a tile and is useful for determining the parameter type that a BAttribute takes or an
 * */
public class BAttributeDatabase {

  private Map<String, BAttribute> attributeMap;
  public BAttributeDatabase(){
    attributeMap = new HashMap<>();
    ArrayList<BAttribute> attributeList = (ArrayList<BAttribute>) FileReader.readCommands("available-attributes");
    addAttributes(attributeList);
  }

  private void addAttributes(ArrayList<BAttribute> attributeList){
    attributeList.forEach(attribute -> {
      this.attributeMap.put(attribute.name, attribute);
    });
  }

  public BAttribute getAttribute(String name){
    return this.attributeMap.get(name);
  }
}
