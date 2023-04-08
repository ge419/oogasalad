package oogasalad.model.engine.actions;

import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.prompt.Prompter;

/**
 * Parameters provided to an action.
 *
 * @param emitter  used to emit events
 * @param prompter used to register user prompts
 * @author Dominic Martinez
 */
public record ActionParams(EventEmitter emitter, Prompter prompter) {

}