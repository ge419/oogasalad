package oogasalad.controller;

import java.util.function.Consumer;
import oogasalad.view.gameplay.HumanPrompter;
import oogasalad.view.gameplay.Gameview;

public interface PrompterFactory {
  HumanPrompter makeDualPrompter(Consumer<Effect> doEffect, Gameview gameview);
//  AIPrompter makeAIPrompter(Consumer<Effect> doEffect);
}
