package oogasalad.model.engine;

public enum PlayerAttribute {
  SCORE("score");

  private final String key;

  PlayerAttribute(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
