package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AttributeSchema {
  private final String name;
  private final List<AttributeMetadata> metadataList;

  public AttributeSchema(String name) {
    this.name = name;
    this.metadataList = new ArrayList<>();
  }

  @JsonCreator
  public AttributeSchema(
      @JsonProperty("name") String name,
      @JsonProperty("fields") List<AttributeMetadata> metadata
  ) {
    this.name = name;
    this.metadataList = new ArrayList<>(metadata);
  }

  @JsonGetter("fields")
  public List<AttributeMetadata> getAllMetadata() {
    return metadataList;
  }

  @JsonIgnore
  public Optional<AttributeMetadata> getMetadata(String key) {
    for (AttributeMetadata metadata : metadataList) {
      if (metadata.getKey().equals(key)) {
        return Optional.of(metadata);
      }
    }

    return Optional.empty();
  }

  public String getName() {
    return name;
  }

  public AttributeList makeAllAttributes() {
    List<Attribute> list = new ArrayList<>();
    for (AttributeMetadata metadata : metadataList) {
      Attribute attr = metadata.makeAttribute();
      Objects.requireNonNull(attr);
      list.add(attr);
    }

    return new AttributeList(list);
  }

  @Override
  public String toString() {
    return "AttributeSchema{" +
        "name='" + name + '\'' +
        ", metadataList=" + metadataList +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttributeSchema schema = (AttributeSchema) o;
    return Objects.equals(name, schema.name) && Objects.equals(metadataList,
        schema.metadataList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, metadataList);
  }
}
