package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A list of attributes that describe a piece of data.
 *
 * @author Dominic Martinez
 */
public class AttributeList {
  List<Attribute> attributeList;

  public AttributeList() {
    attributeList = new ArrayList<>();
  }

  @JsonCreator
  public AttributeList(@JsonProperty("attributes") List<Attribute> attributeList) {
    this.attributeList = new ArrayList<>(attributeList);
  }

  @JsonGetter("attributes")
  public List<Attribute> getAllAttributes() {
    return attributeList;
  }

  @JsonIgnore
  public Optional<Attribute> getAttribute(String key) {
    for (Attribute attribute : attributeList) {
      if (attribute.getKey().equals(key)) {
        return Optional.of(attribute);
      }
    }

    return Optional.empty();
  }
}
