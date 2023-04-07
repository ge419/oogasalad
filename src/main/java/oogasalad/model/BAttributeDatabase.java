package oogasalad.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.FileReader;

/**
 * This utility class hold all attributes for a tile and is useful for determining the parameter type that a BAttribute takes or an
 * */
public class BAttributeDatabase {

  private Map<String, BAttribute> attributeMap;
  public BAttributeDatabase(){
    attributeMap = new HashMap<>();
    ArrayList<BAttribute> attributeList = (ArrayList<BAttribute>);
    try{
      List<File> files = FileReader.readFiles("available-attributes");
    } catch (FileReaderException, IOException e){

    }
    addAttributes(attributeList);
  }

  private void addAttributes(ArrayList<BAttribute> attributeList){
    attributeList.forEach(attribute -> {
      this.attributeMap.put(attribute.getKey(), attribute);
    });
  }

  public BAttribute getAttribute(String name){
    return this.attributeMap.get(name);
  }
}
