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


