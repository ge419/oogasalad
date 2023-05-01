package oogasalad.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import oogasalad.model.attribute.FileReader;
import oogasalad.model.exception.FileReaderException;

@Deprecated
public class BActionDB {

  Logger logger = Logger.getLogger(String.valueOf(FileReader.class));
  private final Map<String, BAction> actionMap = new HashMap<>();

  public BActionDB() {

  }

  public static void main(String[] args) throws IOException, FileReaderException {
    BActionDB db = new BActionDB();
    db.readFiles();
  }

  private void readFiles() {
    try {
      List<File> files = FileReader.readFiles("actions");
      files.iterator().forEachRemaining(file -> {
            try {
              CompletableFuture<BAction> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                  return createData(file);
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              });
              BAction result = completableFuture.get();
              this.actionMap.put(result.getName().toLowerCase(), result);
            } catch (ExecutionException | InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
      );
    } catch (IOException e) {
    } catch (FileReaderException e) {
      logger.info("Failed to read attributes file");
    }
  }

  private BAction createData(File file) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    BAction data = objectMapper.readValue(file, BAction.class);
    return data;
  }
}
