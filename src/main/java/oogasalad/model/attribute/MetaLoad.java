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
public class MetaLoad {
  Logger logger = Logger.getLogger(String.valueOf(FileReader.class));
  private final Map<String, MetaData> metaMap = new HashMap<>();

  public MetaLoad() {
    this.load();
  }

  private void load() {
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
              MetaData result = completableFuture.get();
              this.metaMap.put(file.getName().replaceAll(".json",""), result);
            } catch (ExecutionException | InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
      );
    } catch (FileReaderException | IOException e) {
      logger.info("Failed to read attributes file");
    }
  }


  private MetaData createData(File file) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    MetaData data = objectMapper.readValue(file, MetaData.class);
    return data;
  }

  public String getAttributeType(String key) {
    return this.metaMap.get(key).getType();
  }

}
