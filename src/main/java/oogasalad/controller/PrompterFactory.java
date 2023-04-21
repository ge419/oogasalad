package oogasalad.controller;

import java.util.function.Consumer;
import oogasalad.model.engine.prompt.DualPrompter;

public interface PrompterFactory {
  DualPrompter makeDualPrompter(Consumer<Effect> doEffect);
}
