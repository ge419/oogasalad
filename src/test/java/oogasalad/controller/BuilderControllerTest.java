//package oogasalad.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import oogasalad.model.accesscontrol.dao.GameDao;
//import oogasalad.model.constructable.GameHolder;
//import oogasalad.view.BuilderFactory;
//import oogasalad.view.builder.BuilderView;
//import oogasalad.view.tabexplorer.userpreferences.Languages;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import util.DukeApplicationTest;
//
//public class BuilderControllerTest extends DukeApplicationTest {
//  GameHolder gameHolder;
//  @Mock
//  private BuilderView builder;
//  private BuilderController bc;
//  @Mock
//  GameDao dao = Mockito.mock(GameDao.class);
//  private static final String TEST_LANGUAGE = "en-US";
//  private static final String TEST_GAME_ID = "DEzmBuvsM4H3zLEhvQa0";
//
//  //Languages.ENGLISH.getLocaleStr(),"DEzmBuvsM4H3zLEhvQa0", null)
//
//  @BeforeEach
//  void setup() {
//    bc = new BuilderController(TEST_LANGUAGE, TEST_GAME_ID, dao);
//    builder = bc.getBuilderView();
//    gameHolder = bc.getGameHolder();
//  }
//
//  @Test
//  void gameHolderBoardTest() {
//    //TODO: replace "a"
//    assertTrue(!gameHolder.getBoard().getById("a").isPresent());
//  }
//
//  @Test
//  void gameHolderPlayerTest() {
//
//  }
//
//  @Test
//  void createBoardImageTest() {
//
//  }
//
//  @Test
//  void makeRulesPopupTest() {
//
//  }
//
//}
