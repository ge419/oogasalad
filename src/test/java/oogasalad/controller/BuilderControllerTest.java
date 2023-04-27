package oogasalad.controller;

import oogasalad.model.constructable.GameHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuilderControllerTest {
  GameHolder gameHolder;
  BuilderController bc;

  @BeforeAll
  void setup() {
    gameHolder = GameHolder.createDefaultGame();
    bc = new BuilderController("English");
  }

  @Test
  void saveTest() {


  }

}
