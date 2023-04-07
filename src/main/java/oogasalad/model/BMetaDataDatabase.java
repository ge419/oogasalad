package oogasalad.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This utility class hold all metaData and is useful for determining the parameter type that a BMetaData takes or an
 * */
public class BMetaDataDatabase {

  private Map<String, BMetaData> attributeMap;
  public BMetaDataDatabase(){
    attributeMap = new HashMap<>();
    ArrayList<BMetaData> metaDataList = (ArrayList<BMetaData>) ;
    try{
      List<File> files = FileReader.readFiles("available-attributes");
    } catch (IOException e){

    } catch (FileReaderException e) {
      throw new RuntimeException(e);
    }
    addAttributes(metaDataList);
  }

  private void addAttributes(ArrayList<BMetaData> metaDataList){
    metaDataList.forEach(metaData -> {
      this.attributeMap.put(metaData.getKey(), metaData);
    });
  }

  public BMetaData getMetaData(String name){
    return this.attributeMap.get(name);
  }
}
