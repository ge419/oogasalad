package oogasalad.controller;

import java.util.function.Consumer;
import oogasalad.view.gameplay.DualPrompter;
import oogasalad.view.gameplay.Gameview;

public interface PrompterFactory {
  DualPrompter makeDualPrompter(Consumer<Effect> doEffect, Gameview gameview);
//  AIPrompter makeAIPrompter(Consumer<Effect> doEffect);
}
