package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
public abstract class Attribute {
  private final String name;
  public Attribute(String name) {
    this.name = name;
  }
  public static <T extends Attribute> T getAs(Attribute attribute, Class<T> attrType) {
    return attrType.cast(attribute);
  }
  public String getKey() {
    return this.name;
  }

}
