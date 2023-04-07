package oogasalad.model.engine.actions;

import oogasalad.model.engine.event_loop.EventEmitter;
import oogasalad.model.engine.prompt.Prompter;

public record ActionParams(EventEmitter emitter, Prompter prompter) {

}