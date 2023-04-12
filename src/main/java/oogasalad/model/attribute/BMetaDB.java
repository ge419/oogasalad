package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import oogasalad.model.exception.FileReaderException;

/**
 * This utility class hold all metaData and is useful for determining the parameter type that a BMetaData takes or an
 * */
public class BMetaDB {
  Logger logger = Logger.getLogger(String.valueOf(FileReader.class));
  private final Map<String, MetaData> metaMap = new HashMap<>();

  private void readFiles() {
    try{
      List<File> files = FileReader.readFiles("metadata");
      files.iterator().forEachRemaining(file -> {
            try {
              CompletableFuture<MetaData> completableFuture = CompletableFuture.supplyAsync(() -> {
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
              MetaData result = completableFuture.get();
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

  private MetaData createData(File file) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    MetaData data = objectMapper.readValue(file, MetaData.class);
    return data;
  }

  public static void main(String[] args) throws IOException, FileReaderException {
    BMetaDB db = new BMetaDB();
    db.readFiles();
    System.out.println(db.metaMap.get("color.json").getKey());
  }
}
