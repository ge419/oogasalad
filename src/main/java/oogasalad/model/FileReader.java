package oogasalad.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class FileReader {

   static Logger logger = Logger.getLogger(String.valueOf(FileReader.class));


  public static List<File> readFiles(String path) throws IOException, FileReaderException {
    try {
    return Files.walk(Paths.get(path))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());
    } catch (IOException e){
      String errorMessage = "Failed to read attributes file";
      logger.info(errorMessage);
      // logger.log(Level.ERROR, errorMessage);
      throw new FileReaderException(errorMessage);
    }
  }



}
