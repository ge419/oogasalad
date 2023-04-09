package oogasalad.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Inject;
import oogasalad.model.attribute.AttributeMetadata;
import oogasalad.model.exception.FileReaderException;

/**
 * This utility class hold all metaData and is useful for determining the parameter type that a BMetaData takes or an
 * */
public class BMetaDataDatabase {
   Logger logger = Logger.getLogger(String.valueOf(FileReader.class));


  private Map<String, AttributeMetadata<?>> attributeMap;

  @Inject
  public BMetaDataDatabase(ConstructableFactory factory) throws IOException, FileReaderException {
    attributeMap = new HashMap<>();
    addAttributes(readFiles(factory));
  }

  private List<AttributeMetadata<?>> readFiles(ConstructableFactory factory) throws IOException, FileReaderException{
    List<AttributeMetadata<?>> metaDataList = new ArrayList<>();
    try{
      List<File> files = FileReader.readFiles("metadata");
      files.iterator().forEachRemaining(file -> {
            try {
              metaDataList.add((AttributeMetadata<?>) factory.generate(file));
            } catch (IOException e) {
              // FileReaderException("Error deserialzing MetaData");
            }
          }
      );
    } catch (IOException e){
    } catch (FileReaderException e) {
      logger.info("Failed to read attributes file");
    }
    return metaDataList;
  }
  private void addAttributes(List<AttributeMetadata<?>> metaDataList){
    metaDataList.forEach(metaData -> {
      this.attributeMap.put(metaData.getKey(), metaData);
    });
  }

  public AttributeMetadata<?> getMetaData(String name){
    return this.attributeMap.get(name);
  }

  public static void main(String[] args) throws IOException, FileReaderException {
    BMetaDataDatabase db = new BMetaDataDatabase(new ConstructableFactory());
    System.out.println(db.getMetaData("color").getKey());
  }
}
