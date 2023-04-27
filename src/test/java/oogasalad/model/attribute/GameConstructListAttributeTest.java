package oogasalad.model.attribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class GameConstructListAttributeTest {

  GameConstructListAttribute attribute;
  private static final String KEY = "constructKey";

  @BeforeEach
  public void setUp() {
    attribute = new GameConstructListAttribute(KEY, new ArrayList<>());
  }

  @Test
  public void testVariableRetrieval() {
    assertNotNull(attribute);
    assertEquals(attribute.getKey(), KEY);
    assertEquals(attribute.getGameConstructIds().size(), 0);
  }

  @Test
  public void testGetGameConstructIds() {
    List<String> ids = new ArrayList<>();
    ids.add("id1");
    ids.add("id2");
    attribute = new GameConstructListAttribute(KEY, ids);
    assertEquals(attribute.getGameConstructIds(), ids);
  }

  @Test
  public void testSetGameConstructIds() {
    List<String> ids = new ArrayList<>();
    ids.add("id1");
    ids.add("id2");
    attribute.setGameConstructIds(ids);
    assertEquals(attribute.getGameConstructIds(), ids);
  }
}
