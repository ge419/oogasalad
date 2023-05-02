package oogasalad.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import oogasalad.controller.GameInfo;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.SaveDirectory;
import oogasalad.model.engine.rules.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the current save directory, including saving settings and assets.
 *
 * @author Dominic Martinez
 */
public class SaveManager {

  public static final String BOARD_FILE_NAME = "board.json";
  public static final String RULE_FILE_NAME = "rules.json";
  public static final String SCHEMA_FILE_NAME = "schemas.json";
  public static final String INFO_FILE_NAME = "info.json";
  public static final String ASSETS_DIR_NAME = "assets";
  public static final int MAX_DUP_SEARCH = 500;
  private static final Logger LOGGER = LogManager.getLogger(SaveManager.class);
  private final Path saveDir;
  private final Path assetsDir;
  private final SchemaDatabase schemaDatabase;
  private final GameHolder game;
  private final ObjectMapper mapper;

  @Inject
  public SaveManager(
      @SaveDirectory Path saveDir,
      SchemaDatabase schemaDatabase,
      GameHolder gameHolder,
      ObjectMapper mapper
  ) {
    this.saveDir = saveDir;
    this.schemaDatabase = schemaDatabase;
    this.game = gameHolder;
    this.mapper = mapper;
    this.assetsDir = saveDir.resolve(ASSETS_DIR_NAME);

    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  /**
   * Saves the current {@link GameHolder} to the save directory.
   */
  public void saveGame() {
    ensureSaveDir();

    writeFile(BOARD_FILE_NAME, game.getBoard());
    writeFile(RULE_FILE_NAME, game.getRules().toArray(Rule[]::new));
    writeFile(INFO_FILE_NAME, game.getGameInfo());
    writeFile(SCHEMA_FILE_NAME, schemaDatabase.getCustomSchemas().toArray(ObjectSchema[]::new));
  }

  /**
   * Loads the game into the current {@link GameHolder}
   */
  public void loadGame() {
    ensureSaveDir();

    readFile(BOARD_FILE_NAME, BBoard.class).ifPresent(game::setBoard);
    readFile(RULE_FILE_NAME, Rule[].class).ifPresent(
        rules -> game.setRules(Arrays.stream(rules).toList())
    );
    readFile(INFO_FILE_NAME, GameInfo.class).ifPresent(game::setGameInfo);
    readFile(SCHEMA_FILE_NAME, ObjectSchema[].class).ifPresent(
        schemas -> {
          for (ObjectSchema schema : schemas) {
            schemaDatabase.addCustomSchema(schema);
          }
        }
    );

    schemaDatabase.setRuleListProperty(game.rulesProperty());
  }

  /**
   * Given a path, copy the file to the current asset directory and return the asset filename.
   *
   * @param assetPath path to external asset
   * @return filename to use with {@link #getAssetPath(String)}
   * @throws IOException on IO failure
   */
  public String saveAsset(Path assetPath) throws IOException {
    ensureAssetsDir();

    System.out.println("Path: " + assetPath);
    String filename = assetPath.getFileName().toString();
    System.out.println(filename);
    if (Files.exists(assetsDir.resolve(filename))) {
      filename = generateUniqueAssetFilename(filename);
      System.out.println(filename);
    }

    Path newAssetPath = assetsDir.resolve(filename);
    System.out.println(newAssetPath);
    Files.copy(assetPath, newAssetPath);

    return filename;
  }

  /**
   * Given an asset filename, return the full filepath if it exists.
   *
   * @param assetName filename of asset
   * @return path if file exists, {@link Optional#empty()} otherwise
   */
  public Optional<Path> getAssetPath(String assetName) {
    Path path = assetsDir.resolve(assetName);
    System.out.println("path.toString() = " + path.toString());
    if (Files.exists(path)) {
      return Optional.of(path);
    }
    return Optional.empty();
  }

  private String generateUniqueAssetFilename(String filename) {
    int extensionIndex = filename.indexOf('.');
    if (extensionIndex == -1) {
      extensionIndex = filename.length();
    }

    String basename = filename.substring(0, extensionIndex);
    String extension = filename.substring(extensionIndex);

    for (int i = 1; i < MAX_DUP_SEARCH; i++) {
      String newName = basename + "_" + i + extension;

      if (!Files.exists(assetsDir.resolve(newName))) {
        return newName;
      }
    }

    // Resort to UUID
    String newName = UUID.randomUUID() + extension;
    if (Files.exists(assetsDir.resolve(newName))) {
      // Give up
      throw new SaveManagerException("unable to generate unique asset filename");
    }

    return newName;
  }

  private void writeFile(String relativePath, Object object) {
    Path path = saveDir.resolve(relativePath);

    try {
      mapper.writeValue(path.toFile(), object);
    } catch (Exception e) {
      LOGGER.error("unable to write save file {}", path, e);
    }
  }

  private <T> Optional<T> readFile(String relativePath, Class<? extends T> clazz) {
    Path path = saveDir.resolve(relativePath);
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    try {
      return Optional.of(mapper.readValue(path.toFile(), clazz));
    } catch (Exception e) {
      LOGGER.error("unable to read save file {}", path, e);
      return Optional.empty();
    }
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
      throw new SaveManagerException("save directory exists as file", e);
    } catch (IOException e) {
      LOGGER.error("unrecoverable IO exception", e);
      throw new SaveManagerException("unrecoverable IO exception", e);
    }
  }
}
