package oogasalad.model.attribute;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import oogasalad.model.exception.FileReaderException;


public class FileReader {

  static Logger logger = Logger.getLogger(String.valueOf(FileReader.class));


  public static List<File> readFiles(String path) throws IOException, FileReaderException {
    URL resource = FileReader.class.getResource(String.format("/%s", path));
    try {
      return Files.walk(Paths.get(resource.getPath()))
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      String errorMessage = "Failed to read attributes file";
      logger.info(errorMessage);
      // logger.log(Level.ERROR, errorMessage);
      throw new FileReaderException(errorMessage);
    }
  }


}
