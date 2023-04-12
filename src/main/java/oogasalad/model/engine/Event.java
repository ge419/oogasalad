package oogasalad.model.engine;

import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.engine.events.EventType;

/**
 * A specific game event.
 */
public record Event(EventType type, Map<String, Attribute> attributeMap) {
  // TODO: wrap map in class
}