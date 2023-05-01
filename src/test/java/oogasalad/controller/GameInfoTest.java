package oogasalad.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameInfoTest {
  GameInfo gameInfo;

  @BeforeEach
  void setup() {
    gameInfo = new GameInfo();
  }

  @Test
  void testWidth() {
    gameInfo.setWidth(400);
    assertEquals(gameInfo.getWidth(), 400);
  }

  @Test
  void testHeight() {
    gameInfo.setHeight(200);
    assertEquals(gameInfo.getHeight(), 200);
  }


}
