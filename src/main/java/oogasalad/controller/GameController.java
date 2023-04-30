package oogasalad.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.stage.Stage;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.Rule;
import oogasalad.util.SaveManager;
import oogasalad.view.ViewFactory;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.gameplay.SetDieRule;

public class GameController {

  private final Engine engine;
  private final Gameview gv;
  private final GameHolder game;
  private final PrompterFactory prompterFactory;
  // TODO get from player
  private final Prompter prompter;
  private final Injector injector;
  LinkedList<Effect> effects;

  public GameController(Path saveDir) {
    this.injector = Guice.createInjector(new GameControllerModule(saveDir));
    injector.getInstance(SaveManager.class).loadGame();

    gv = injector.getInstance(ViewFactory.class).makeGameview(this);
    this.effects = new LinkedList<>();
    this.engine = injector.getInstance(Engine.class);
    this.game = injector.getInstance(GameHolder.class);
    this.prompterFactory = injector.getInstance(PrompterFactory.class);
    this.prompter = prompterFactory.makeHumanPrompter(
        effect -> effects.add(effect),
        gv
    );
  }

  public void setGame(Stage stage) throws IOException {
    gv.renderGameview(stage);
    List<Rule> ruleList = new ArrayList<>(game.getRules());
    ruleList.add(new SetDieRule(gv.getDie()));
    engine.setRules(ruleList);
    this.run();
  }

  public void run() {
    engine.runNextAction(prompter);
    this.doEffect();
  }

  public void doEffect() {
    if (!effects.isEmpty()) {
      effects.poll().present(this::doEffect);
    } else {
      this.run();
    }
  }

}

