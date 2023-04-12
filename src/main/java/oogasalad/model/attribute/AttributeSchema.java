package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public class AttributeSchema {
  private final String name;
  private final Map<String, Metadata> metadataMap;

  public AttributeSchema(String name) {
    this.name = name;
    this.metadataMap = new TreeMap<>();
  }

  @JsonCreator
  public AttributeSchema(
      @JsonProperty("name") String name,
      @JsonProperty("fields") Collection<Metadata> metadataList
  ) {
    this.name = name;
    this.metadataMap = new TreeMap<>();
    for (Metadata metadata : metadataList) {
      metadataMap.put(metadata.getKey(), metadata);
    }
  }

  @JsonGetter("fields")
  public List<Metadata> getAllMetadata() {
    return metadataMap.values().stream().toList();
  }

  @JsonIgnore
  public Optional<Metadata> getMetadata(String key) {
    return Optional.ofNullable(metadataMap.get(key));
  }

  public String getName() {
    return name;
  }

  public List<Attribute> makeAllAttributes() {
    List<Attribute> list = new ArrayList<>();
    for (Metadata metadata : getAllMetadata()) {
      Attribute attr = metadata.makeAttribute();
      Objects.requireNonNull(attr);
      list.add(attr);
    }

    return list;
  }

  public boolean containsKey(String key) {
    return metadataMap.containsKey(key);
  }

  @Override
  public String toString() {
    return "AttributeSchema{" +
        "name='" + name + '\'' +
        ", metadataList=" + metadataMap +
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
    return Objects.equals(name, schema.name) && Objects.equals(metadataMap,
        schema.metadataMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, metadataMap);
  }
}
