package oogasalad.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import javax.inject.Inject;
import oogasalad.model.attribute.AttributeMetadata;
import oogasalad.model.exception.FileReaderException;

/**
 * This utility class hold all metaData and is useful for determining the parameter type that a BMetaData takes or an
 * */
public class BMetaDB {
  Logger logger = Logger.getLogger(String.valueOf(FileReader.class));
  private Map<String, AttributeMetadata> metaMap = new HashMap<>();

  public BMetaDB() {

  }

  private void readFiles() {
    try{
      List<File> files = FileReader.readFiles("metadata");
      files.iterator().forEachRemaining(file -> {
            try {
              CompletableFuture<AttributeMetadata> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                  return createData(file);
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              });
              while (!completableFuture.isDone()) {
                System.out.println("CompletableFuture is not finished yet...");
              }
              System.out.println(file.getName());
              AttributeMetadata result = completableFuture.get();
              this.metaMap.put(file.getName(), result);
            } catch (ExecutionException e) {
              throw new RuntimeException(e);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
      );
    } catch (IOException e){
    } catch (FileReaderException e) {
      logger.info("Failed to read attributes file");
    }
  }
//  private void addAttributes(List<BMetaData> metaDataList){
//    metaDataList.forEach(metaData -> {
//      this.metaMap.put(metaData.getKey(), metaData);
//    });
//  }

  private AttributeMetadata createData(File file) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    AttributeMetadata data = objectMapper.readValue(file, AttributeMetadata.class);
    return data;
  }

  public static void main(String[] args) throws IOException, FileReaderException {
    BMetaDB db = new BMetaDB();
    db.readFiles();
    System.out.println(db.metaMap.get("color.json").getKey());
  }
}
