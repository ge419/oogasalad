//package oogasalad.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import oogasalad.model.constructable.GameHolder;
//import oogasalad.view.BuilderFactory;
//import oogasalad.view.tabexplorer.userpreferences.Languages;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class BuilderControllerTest {
//  GameHolder gameHolder;
//  BuilderController bc;
//
//  @BeforeEach
//  void setup() {
//    bc = new BuilderController(Languages.ENGLISH.getLocaleStr(),"DEzmBuvsM4H3zLEhvQa0", null);
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
