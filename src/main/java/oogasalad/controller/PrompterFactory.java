package oogasalad.controller;

import java.util.function.Consumer;
import oogasalad.model.engine.prompt.AIPrompter;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.gameplay.HumanPrompter;

public interface PrompterFactory {

  HumanPrompter makeHumanPrompter(Consumer<Effect> doEffect, Gameview gameview);

  AIPrompter makeAIPrompter(Consumer<Effect> doEffect, Gameview gameview);
}
