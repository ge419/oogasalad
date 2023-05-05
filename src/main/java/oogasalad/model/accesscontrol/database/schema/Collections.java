package oogasalad.model.accesscontrol.database.schema;

public enum Collections {
  USERS("users"),
  GAMES("games");

  private final String collections;

  Collections(String collections) {
    this.collections = collections;
  }

  public String getString() {
    return collections;
  }
}
