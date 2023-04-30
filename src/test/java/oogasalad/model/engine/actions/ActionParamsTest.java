package oogasalad.model.engine.actions;

import static org.junit.Assert.assertEquals;

import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.prompt.Prompter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActionParamsTest {

  @Mock
  private EventEmitter emitter;

  @Mock
  private Prompter prompter;

  @Test
  public void testGetEmitter() {
    ActionParams params = new ActionParams(emitter, prompter);
    assertEquals(emitter, params.emitter());
  }

  @Test
  public void testGetPrompter() {
    ActionParams params = new ActionParams(emitter, prompter);
    assertEquals(prompter, params.prompter());
  }
}
