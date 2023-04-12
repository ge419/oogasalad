package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PositionAttribute extends Attribute{

  private final ListProperty<Double> value;

  @JsonCreator
  public PositionAttribute(@JsonProperty("key") String key, @JsonProperty("value") ArrayList<Double> value) {
    super(key);
    this.value = new SimpleListProperty<>(FXCollections.observableList(value));
  }

  public static PositionAttribute from(Attribute attr) {
    return Attribute.getAs(attr, PositionAttribute.class);
  }

  public List<Double> getValue() {
    return this.value.get();
  }

  public void setValue(List<Double> value) {
    this.value.set((ObservableList<Double>) value);
  }

  @Override
  public String toString() {
    return String.format("PositionAttribute{x: %.2f, y: %.2f}", value.get().get(0), value.get().get(1));
  }
}
