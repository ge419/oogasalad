package oogasalad.model.accesscontrol.database.schema;

public enum ReviewSchema {
  AUTHOR("author"),
  DATE_POSTED("date_posted"),
  REVIEW("review");

  private final String fieldName;

  ReviewSchema(String fieldName) {
    this.fieldName = fieldName;
  }
  public String getFieldName() {
    return fieldName;
  }
}
