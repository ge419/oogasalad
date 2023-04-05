package oogasalad.model;

public class BAttribute {
  private final String key;
  private String value;
  private String type;
  private String defaultValue;
  private boolean isEditable;

  public BAttribute(String key, String val) {
    this.key = key;
    this.value = val;
  }

  public String getValue() {
    return this.value;
  }

  public void updateValue(String val) {
    this.value = val;
  }

  public boolean getEditStatus() {
    return this.isEditable;
  }

  @Override
  public String toString() {
    return this.key;
  }
}
