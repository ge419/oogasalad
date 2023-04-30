package oogasalad.model.accesscontrol.database.schema;

public enum GameSchema {
  AUTHOR("author"),
  DATE_CREATED("date_created"),
  DESCRIPTION("description"),
  GAME_DATA("game_data"),
  GENRE("genre"),
  NUMBER_OF_PLAYS("number_of_plays"),
  REVIEWS("reviews"),
  SUBSCRIPTION_COUNT("subscription_count"),
  THUMBNAIL("thumbnail"),
  TITLE("title");

  private final String fieldName;

  GameSchema(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return fieldName;
  }
}