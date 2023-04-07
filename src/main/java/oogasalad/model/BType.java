package oogasalad.model;

public enum BType {
  INT(Integer.class),
  DOUBLE(Double.class),
  STRING(String.class),
  DROPDOWN(String.class),
  COLOR(String.class),
  TILE(Integer.class);

  private Class<?> typeClass;

  BType(Class<?> clazz) {
    this.typeClass = clazz;
  }

  public Class<?> getTypeClass() {
    return typeClass;
  }

}
