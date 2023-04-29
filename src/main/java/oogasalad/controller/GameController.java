package oogasalad.controller;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.stage.Stage;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.DieMoveRule;
import oogasalad.model.engine.rules.TurnRule;
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

  @Inject
  public GameController(
      Injector outsideInjector
  ) {
    this.injector = outsideInjector.createChildInjector(new GameControllerModule());
    gv = injector.getInstance(ViewFactory.class).makeGameview(this);
    this.effects = new LinkedList<>();
    this.engine = injector.getInstance(Engine.class);
    this.game = injector.getInstance(GameHolder.class);
    this.prompterFactory = injector.getInstance(PrompterFactory.class);
    this.prompter = prompterFactory.makeDualPrompter(
        effect -> effects.add(effect),
        gv
    );
  }

  public void setGame(Stage stage) throws IOException {
    gv.renderGameview(stage);
    engine.setRules(
        List.of(
//            injector.getInstance(NumberOfPlayersRule.class),
            injector.getInstance(TurnRule.class),
            injector.getInstance(DieMoveRule.class),
            injector.getInstance(BuyTileRule.class),
            new SetDieRule(gv.getDie())
        )
    );
    this.run();
  }

  public void run() {
    engine.runNextAction(prompter);
    this.doEffect();
  }

  public void doEffect() {
    if (!effects.isEmpty()) {
      // If there is a pending effect, perform it and do the next one once done
      effects.poll().present(this::doEffect);
    } else {
      this.run();
    }
  }

}

