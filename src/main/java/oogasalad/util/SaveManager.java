package oogasalad.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.SaveDirectory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the current save directory, including saving settings and assets.
 *
 * @author Dominic Martinez
 */
public class SaveManager {

  public static final String SETTINGS_FILE_NAME = "settings.json";
  public static final String ASSETS_DIR_NAME = "assets";
  private static final Logger LOGGER = LogManager.getLogger(SaveManager.class);
  private final Path saveDir;
  private final Path assetsDir;
  private final ObjectMapper mapper;

  @Inject
  public SaveManager(@SaveDirectory Path saveDir) {
    this.saveDir = saveDir;
    this.assetsDir = saveDir.resolve(ASSETS_DIR_NAME);
    this.mapper = new ObjectMapper();
  }

  public void saveGame(GameHolder game) {
    ensureSaveDir();
    Path settingsFile = saveDir.resolve(SETTINGS_FILE_NAME);

    try {
      mapper.writeValue(settingsFile.toFile(), game);
    } catch (IOException e) {
      LOGGER.error("unrecoverable IO exception while saving file", e);
    }
  }

  public GameHolder loadGame() {
    ensureSaveDir();
    Path settingsFile = saveDir.resolve(SETTINGS_FILE_NAME);

    if (!Files.exists(settingsFile)) {
      LOGGER.info("save file does not exist; creating default");
      return GameHolder.createDefaultGame();
    }

    try {
      return mapper.readValue(settingsFile.toFile(), GameHolder.class);
    } catch (Exception e) {
      LOGGER.error("error reading save file", e);
      return GameHolder.createDefaultGame();
    }
  }

  public void saveAsset(Path assetPath) throws IOException {
    ensureAssetsDir();

    String filename = assetPath.getFileName().toString();
    int extensionIndex = filename.lastIndexOf('.');
    String newFilename = UUID.randomUUID().toString();

    if (extensionIndex != -1) {
      newFilename += filename.substring(extensionIndex);
    }

    Path newAssetPath = assetsDir.resolve(newFilename);

    Files.copy(assetPath, newAssetPath);
  }

  public Path getAssetPath(String assetName) {
    return assetsDir.resolve(assetName);
  }

  private void ensureSaveDir() {
    ensureDir(saveDir);
  }

  private void ensureAssetsDir() {
    ensureDir(saveDir.resolve(ASSETS_DIR_NAME));
  }

  private void ensureDir(Path dir) {
    try {
      Files.createDirectories(dir);
    } catch (FileAlreadyExistsException e) {
      LOGGER.warn("save directory exists as file", e);
      throw new RuntimeException("save directory exists as file", e);
    } catch (IOException e) {
      LOGGER.error("unrecoverable IO exception", e);
      throw new RuntimeException(e);
    }
  }
}
