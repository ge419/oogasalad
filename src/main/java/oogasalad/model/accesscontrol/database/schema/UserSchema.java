package oogasalad.model.accesscontrol.database.schema;

public enum UserSchema {
  AVATAR("avatar"),
  AGE("age"),
  DATE_JOINED("date_joined"),
  EMAIL("email"),
  GAMES("games"),
  NAME("name"),
  NUMGAMESPLAYED("number_of_games_played"),
  USERNAME("username"),
  PASSWORD("password"),
  PREFERRED_LANGUAGE("preferred_language"),
  PREFERRED_THEME("preferred_theme"),
  PRONOUNS("pronouns");

  private final String fieldName;

  UserSchema(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return fieldName;
  }
}