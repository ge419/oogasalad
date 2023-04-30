package oogasalad.model.accesscontrol.database.schema;

public enum ReviewsSchema {
  AUTHOR("author"),
  DATE_POSTED("date_posted"),
  REVIEW("review");

  private final String fieldName;

  ReviewsSchema(String fieldName) {
    this.fieldName = fieldName;
  }
  public String getFieldName() {
    return fieldName;
  }
}
