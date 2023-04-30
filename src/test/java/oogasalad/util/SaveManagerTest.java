package oogasalad.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import oogasalad.controller.GameInfo;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.GameHolderModule;
import oogasalad.model.constructable.SaveManagerModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaveManagerTest {

  Path testDir;
  Path exampleFile;
  SchemaDatabase mockSchemaDatabase;
  SaveManager saveManager;
  SaveManager secondSaveManager;
  GameHolder gameHolder;
  GameHolder secondGameHolder;

  @BeforeEach
  void setUp() throws URISyntaxException, IOException {
    // TODO: Can we do this in some system temporary directory?
    testDir = Files.createTempDirectory("savedir");
    exampleFile = Path.of(getClass().getResource("pup.jpg").toURI());

    // We have two versions of each class to simulate two versions of the application
    // communicating only through save files

    mockSchemaDatabase = mock(SchemaDatabase.class);

    Injector injector = createInjector();
    saveManager = injector.getInstance(SaveManager.class);
    gameHolder = injector.getInstance(GameHolder.class);

    Injector secondInjector = createInjector();
    secondSaveManager = secondInjector.getInstance(SaveManager.class);
    secondGameHolder = secondInjector.getInstance(GameHolder.class);
  }

  private Injector createInjector() {
    return Guice.createInjector(
        new SaveManagerModule(testDir),
        new GameHolderModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(mockSchemaDatabase)
    );
  }

  @AfterEach
  void tearDown() throws IOException {
    // https://stackoverflow.com/questions/779519/delete-directories-recursively-in-java/27917071#27917071
    Files.walkFileTree(testDir, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
      }
    });
  }

  @Test
  void saveGameNewDir() throws IOException {
    Files.delete(testDir);
    testGameSaved();
  }

  @Test
  void saveGameExistingDir() throws IOException {
    testGameSaved();
  }

  @Test
  void saveGameWithDirectoryAsFile() throws IOException {
    Files.delete(testDir);
    Files.createFile(testDir);
    assertThrows(SaveManagerException.class, () -> saveManager.saveGame());
  }

  private void testGameSaved() {
    String gameTitle = "Test game";
    gameHolder.setGameInfo(new GameInfo().setTitle(gameTitle));
    saveManager.saveGame();

    assertTrue(Files.exists(testDir));

    secondSaveManager.loadGame();
    assertEquals(gameTitle, secondGameHolder.getGameInfo().getTitle());
  }

  @Test
  void saveAsset() throws IOException {
    String filename = saveManager.saveAsset(exampleFile);
    assertEquals("pup.jpg", filename);

    Path assetPath = saveManager.getAssetPath(filename).get();

    assertEquals(filename, assetPath.getFileName().toString());
    assertTrue(Files.exists(assetPath));
    assertEquals(-1, Files.mismatch(exampleFile, assetPath));
  }

  @Test
  void saveDuplicateAsset() throws IOException {
    String filename = saveManager.saveAsset(exampleFile);
    assertEquals("pup.jpg", filename);
    filename = saveManager.saveAsset(exampleFile);
    assertEquals("pup_1.jpg", filename);
    filename = saveManager.saveAsset(exampleFile);
    assertEquals("pup_2.jpg", filename);

    Path assetPath = saveManager.getAssetPath("pup.jpg").get();
    assertEquals("pup.jpg", assetPath.getFileName().toString());

    assetPath = saveManager.getAssetPath("pup_2.jpg").get();
    assertEquals("pup_2.jpg", assetPath.getFileName().toString());

    assertTrue(Files.exists(assetPath));
    assertEquals(-1, Files.mismatch(exampleFile, assetPath));
  }

  @Test
  void getNonexistentAsset() {
    assertTrue(saveManager.getAssetPath("notHere").isEmpty());
  }

  @Test
  void saveNonexistentAsset() {
    Path badPath = Path.of("randomTestfile");
    assertThrows(IOException.class, () -> saveManager.saveAsset(badPath));
  }
}