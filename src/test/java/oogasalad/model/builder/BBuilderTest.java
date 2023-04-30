//TODO: complete tests for BBuilder

// package oogasalad.model.builder;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.awt.Dimension;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import oogasalad.model.builder.BBuilder;
//import oogasalad.model.builder.BBuilderAPI;
//import oogasalad.model.constructable.BBoard;
//import oogasalad.view.Coordinate;
//import oogasalad.view.builder.board.BoardImage;
//import oogasalad.view.builder.board.ImmutableBoardInfo;
//import oogasalad.view.builder.gameholder.ImmutableGameHolder;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class BBuilderTest {
//
//  private BBuilderAPI builder;
//  private ImmutableGameHolder holder;
//  private BBoard board;
//
//  private final String gameTitle = "Test Game";
//  private final String directoryPath = "test/game";
//  private final String filePath = "test/game/game.json";
//
//  @BeforeEach
//  void setUp() {
//    builder = new BBuilder();
//    holder = createImmutableGameHolder();
//    board = createBBoard();
//  }
//
//  @AfterEach
//  void tearDown() throws IOException {
//    Files.deleteIfExists(Paths.get(filePath));
//    Files.deleteIfExists(Paths.get(directoryPath + "/tiles.json"));
//    Files.deleteIfExists(Paths.get(directoryPath + "/players.json"));
//    Files.deleteIfExists(Paths.get(directoryPath + "/settings.json"));
//    File imagesDir = new File(directoryPath + "/images");
//    if (imagesDir.exists()) {
//      Arrays.stream(imagesDir.listFiles()).forEach(file -> file.delete());
//      imagesDir.delete();
//    }
//  }
//
//  @Test
//  void testExtractData() {
//    Map<String, Object> expectedDataMap = createExpectedDataMap();
//    Map<String, Object> actualDataMap = builder.extractData(holder, board);
//    assertEquals(expectedDataMap, actualDataMap);
//  }
//
//  @Test
//  void testSave() throws IOException {
//    builder.save(holder, board);
//    assertTrue(new File(filePath).exists(), "Game file not saved");
//    assertTrue(new File(directoryPath + "/tiles.json").exists(), "Tiles file not saved");
//    assertTrue(new File(directoryPath + "/players.json").exists(), "Players file not saved");
//    assertTrue(new File(directoryPath + "/settings.json").exists(), "Settings file not saved");
//
//    // Check that images were saved
//    File imagesDir = new File(directoryPath + "/images");
//    assertTrue(imagesDir.exists(), "Images directory not created");
//    for (BoardImage image : holder.getBoardInfo().getBoardImages()) {
//      assertTrue(new File(imagesDir, image.imagePath() + ".png").exists(),
//          "Image file not saved: " + image.imagePath());
//    }
//  }
//
//  @Test
//  public void testLoad() {
//  }
//
//  private ImmutableGameHolder createImmutableGameHolder() {
//    List<BoardImage> boardImages = createBoardImages();
//    Dimension boardSize = new Dimension(10, 10);
//    ImmutableBoardInfo boardInfo = new ImmutableBoardInfo();
//    return new ImmutableGameHolder();
//  }
//
//  private List<BoardImage> createBoardImages(){
//    return List.of(new BoardImage("", new Coordinate(0.0,.0), new Dimension()));
//  }
//}