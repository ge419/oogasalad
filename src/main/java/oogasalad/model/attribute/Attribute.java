package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@JsonTypeInfo(use = Id.CLASS)
public abstract class Attribute {

  private static final Logger LOGGER = LogManager.getLogger(Attribute.class);
  private final String key;

  protected Attribute(String key) {
    this.key = key;
  }

  public static <T extends Attribute> T getAs(Attribute attr, Class<T> clazz) {
    try {
      return clazz.cast(attr);
    } catch (ClassCastException e) {
      LOGGER.fatal("failed to perform attribute cast", e);
      throw e;
    }
  }

  public String getKey() {
    return key;
  }
}
