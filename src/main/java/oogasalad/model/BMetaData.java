package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BMetaData {
  private String key;
  private final BType type;
  private final StringProperty name;
  private final StringProperty description;
  private final BooleanProperty editable;
  private final BooleanProperty viewable;
  private final ObjectProperty<?> defaultVal;

  public BMetaData(String key, BType type) {
    this.key = key;
    this.type = type;
    name = new SimpleStringProperty();
    description = new SimpleStringProperty();
    editable = new SimpleBooleanProperty();
    viewable = new SimpleBooleanProperty();
    defaultVal = new SimpleObjectProperty<>();
  }
  public String getKey() {return key;}
}
