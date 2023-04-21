package oogasalad.controller;

import com.google.inject.AbstractModule;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.EventHandlerManager;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.SimpleEngine;
import oogasalad.model.engine.SimpleEventHandlerManager;
import oogasalad.model.engine.prompt.DualPrompter;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.view.gameplay.Gameview.MyPrompter;

public class GameControllerModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new EngineModule());
    // TODO don't take prompter through constructor
    bind(Prompter.class).to(DualPrompter.class);
  }
}
