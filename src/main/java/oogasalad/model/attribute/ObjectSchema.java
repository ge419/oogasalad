package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.List;
import java.util.Optional;

/**
 * Describes the attributes available on an object.
 *
 * @author Dominic Martinez
 */
@JsonTypeInfo(use = Id.CLASS)
public interface ObjectSchema {

  /**
   * @return name of schema
   */
  String getName();

  /**
   * Returns a list of the {@link AbstractMetadata} for each field of the schema.
   */
  List<Metadata> getAllMetadata();

  /**
   * Returns the metadata for a particular field.
   *
   * @param key string key of the field
   * @return an {@link Optional#of(Object)} if the key exists, {@link Optional#empty()} otherwise
   */
  Optional<Metadata> getMetadata(String key);

  /**
   * Returns a list of attributes matching this schema, with appropriate default values.
   */
  List<Attribute> makeAllAttributes();
}
