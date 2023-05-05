package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstraction that represents the attributes of the different Game Constructs including String,
 * Tile, Boolean, Color, List, etc. Attributes to represent different data forms.
 *
 * @author Jay Yoon
 */
@JsonTypeInfo(use = Id.CLASS)
public abstract class AbstractAttribute implements Attribute {

  private static final Logger LOGGER = LogManager.getLogger(AbstractAttribute.class);
  private final String key;

  protected AbstractAttribute(String key) {
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

  @Override
  public String getKey() {
    return key;
  }
}
