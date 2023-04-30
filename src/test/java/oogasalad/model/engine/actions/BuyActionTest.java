package oogasalad.model.engine.actions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ResourceBundle;

import oogasalad.model.engine.prompt.TestPrompterNegative;
import oogasalad.model.engine.prompt.TestPrompterPositive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oogasalad.model.constructable.Player;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.prompt.AIPrompter;

public class BuyActionTest {

  private ResourceBundle resourceBundle;
  private Runnable buyProp;
  private ActionParams actionParams;
  private BuyAction buyAction;

  @BeforeEach
  public void setUp() {
    resourceBundle = mock(ResourceBundle.class);
    when(resourceBundle.getString(any(String.class))).thenReturn("");
    buyProp = mock(Runnable.class);
    buyAction = new BuyAction(buyProp, resourceBundle);
  }

  @Test
  public void testBuy() {
    actionParams = new ActionParams(mock(EventEmitter.class), new TestPrompterPositive());
    buyAction.runAction(actionParams);
    verify(buyProp).run();
  }

  @Test
  public void testDoNotBuy() {
    actionParams = new ActionParams(mock(EventEmitter.class), new TestPrompterNegative());
    buyAction.runAction(actionParams);
    verify(buyProp, never()).run();
  }
}


