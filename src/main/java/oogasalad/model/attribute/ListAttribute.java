package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public abstract class ListAttribute<T extends Attribute> extends Attribute {
  private List<T> values;

  @JsonCreator
  protected ListAttribute(@JsonProperty("key") String key) {
    super(key);
    values = new ArrayList<>();
  }

  public List<T> getValues() {
    return values;
  }

  public void setValues(List<T> values) {
    this.values = values;
  }
}
