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
import oogasalad.model.observers.GameObserver;
import oogasalad.util.SaveManager;
import oogasalad.view.ViewFactory;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.gameplay.SetDieRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initially sets up the engine with a set of {@link Rule} Controls the engine to run the next
 * action on action queue by turn Presents UI effects to {@link Gameview}
 *
 * @Author Jay Yoon
 */
public class GameController implements GameObserver {

  private static final Logger LOGGER = LogManager.getLogger(GameController.class);
  private final Engine engine;
  private final Gameview gv;
  private final GameHolder game;
  private final Prompter prompter;
  private final LinkedList<Effect> effects;

  public GameController(String language, Path saveDir) {
    Injector injector = Guice.createInjector(new GameControllerModule(saveDir, language));
    injector.getInstance(SaveManager.class).loadGame();
    gv = injector.getInstance(ViewFactory.class).makeGameview(this);
    this.effects = new LinkedList<>();
    this.engine = injector.getInstance(Engine.class);
    this.game = injector.getInstance(GameHolder.class);
    PrompterFactory prompterFactory = injector.getInstance(PrompterFactory.class);
    this.prompter = prompterFactory.makeHumanPrompter(
        effects::add,
        gv
    );
    game.register(this);
  }

  /**
   * sets the Gameview stage to gameStage sets the engine with the list of backend rules and
   * creates/adds frontend rules starts the engine
   *
   * @param gameStage
   */
  public void setGame(Stage gameStage) {
    try {
      gv.renderGameview(gameStage);
    } catch (IOException e) {
      LOGGER.fatal("unable to read save directory");
      return;
    }
    List<Rule> ruleList = new ArrayList<>(game.getRules());
    ruleList.add(new SetDieRule(gv.getDie()));
    engine.setRules(ruleList);
    this.run();
  }

  /**
   * runs the next Action on engine action queue if there is pending effect, runs the UI effect
   *
   * @see GameController#doEffect()
   */
  public void run() {
    engine.runNextAction(prompter);
    this.doEffect();
  }

  private void doEffect() {
    if (!effects.isEmpty()) {
      effects.poll().present(this::doEffect);
    } else {
      this.run();
    }
  }
}

