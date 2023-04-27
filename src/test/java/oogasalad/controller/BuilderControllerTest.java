package oogasalad.controller;

import static org.junit.jupiter.api.Assertions.*;

import oogasalad.model.constructable.GameHolder;
import oogasalad.view.BuilderFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuilderControllerTest {
  GameHolder gameHolder;
  BuilderController bc;
//  BuilderFactory builderFactory;

  @BeforeEach
  void setup() {
//    builderFactory =
    gameHolder = GameHolder.createDefaultGame();
//    bc = new BuilderController("English", );
  }

  @Test
  void gameHolderBoardTest() {
    //TODO: replace "a"
    assertTrue(!gameHolder.getBoard().getById("a").isPresent());
  }

  @Test
  void gameHolderPlayerTest() {

  }

}
