package oogasalad.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import oogasalad.controller.GameInfo;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.SaveDirectory;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
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
  private static final Logger LOGGER = LogManager.getLogger(SaveManager.class);
  private final Path saveDir;
  private final Path assetsDir;
  private final SchemaDatabase schemaDatabase;
  private final GameHolder game;

  @Inject
  public SaveManager(
      @SaveDirectory Path saveDir,
      SchemaDatabase schemaDatabase,
      GameHolder gameHolder
  ) {
    this.saveDir = saveDir;
    this.schemaDatabase = schemaDatabase;
    this.game = gameHolder;
    this.assetsDir = saveDir.resolve(ASSETS_DIR_NAME);
  }

  public void saveGame() {
    ensureSaveDir();

    ObjectMapper mapper = makeMapper();

    writeFile(mapper, BOARD_FILE_NAME, game.getBoard());
    writeFile(mapper, RULE_FILE_NAME, game.getRules());
    writeFile(mapper, INFO_FILE_NAME, game.getGameInfo());
    writeFile(mapper, SCHEMA_FILE_NAME, schemaDatabase.getCustomSchemas());
  }

  public void loadGame() {
    ensureSaveDir();

    ObjectMapper mapper = makeMapper();

    readFile(mapper, BOARD_FILE_NAME, BBoard.class).ifPresent(game::setBoard);
    readFile(mapper, RULE_FILE_NAME, Rule[].class).ifPresent(
        rules -> game.setRules(Arrays.stream(rules).toList())
    );
    readFile(mapper, INFO_FILE_NAME, GameInfo.class).ifPresent(game::setGameInfo);
    readFile(mapper, SCHEMA_FILE_NAME, ObjectSchema[].class).ifPresent(
        schemas -> {
          for (ObjectSchema schema : schemas) {
            schemaDatabase.addCustomSchema(schema);
          }
        }
    );
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

  private void writeFile(ObjectMapper mapper, String relativePath, Object object) {
    Path path = saveDir.resolve(relativePath);

    try {
      mapper.writeValue(path.toFile(), object);
    } catch (Exception e) {
      LOGGER.error("unable to write save file {}", path, e);
    }
  }

  private <T> Optional<T> readFile(ObjectMapper mapper, String relativePath,
      Class<? extends T> clazz) {
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

  private ObjectMapper makeMapper() {
    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        new AttributeModule(),
        new SaveManagerModule(saveDir),
        new EngineModule()
    );

    ObjectMapper mapper = injector.getInstance(ObjectMapper.class);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);

    return mapper;
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
