package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import oogasalad.model.constructable.GameConstruct;

/**
 * A rule that can also be edited on the frontend via attributes.
 */
@JsonTypeInfo(use = Id.CLASS)
public interface EditableRule extends Rule, GameConstruct {

}
