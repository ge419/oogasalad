//package oogasalad.view.gameplay;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import javafx.stage.Stage;
//import oogasalad.controller.GameController;
//import oogasalad.controller.GameControllerModule;
//import oogasalad.util.SaveManager;
//import oogasalad.view.ViewFactory;
//import oogasalad.view.tabexplorer.userpreferences.Languages;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//public class GameviewTest extends ApplicationTest {
//
//  @Override
//  public void start(Stage primaryStage) throws IOException {
//    GameController dummyGameController = new GameController(Languages.ENGLISH.getLocaleStr(),
//        Paths.get("data", "dummy"));
//    Injector injector = Guice.createInjector(
//        new GameControllerModule(Paths.get("data", "dummy"),
//            Languages.ENGLISH.getLocaleStr()));
//    injector.getInstance(SaveManager.class).loadGame();
//    Gameview dummyGameView = injector.getInstance(ViewFactory.class).makeGameview(dummyGameController);
//    dummyGameView.renderGameview(primaryStage);
//  }
//
//  @Test
//  public void testRenderGameview() {
//    assertTrue(lookup("#Board").query().isVisible());
//    assertTrue(lookup("#Die").query().isVisible());
//    assertTrue(lookup("#Tiles").query().isVisible());
//    assertTrue(lookup("#Pieces").query().isVisible());
//  }
//
//}
