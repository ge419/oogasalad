package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerListAttributeTest {

  private static final String KEY = "playerListKey";
  private PlayerListAttribute attribute;

  @BeforeEach
  public void setUp() {
    attribute = new PlayerListAttribute(KEY, new ArrayList<>());
  }

  @Test
  public void testVariableRetrieval() {
    assertNotNull(attribute);
    assertEquals(attribute.getKey(), KEY);
    assertEquals(attribute.getGameConstructIds().size(), 0);
  }

  @Test
  public void testGetPlayerIds() {
    List<String> ids = new ArrayList<>();
    ids.add("player1");
    ids.add("player2");
    attribute = new PlayerListAttribute(KEY, ids);
    assertEquals(attribute.getGameConstructIds(), ids);
  }

  @Test
  public void testSetPlayerIds() {
    List<String> ids = new ArrayList<>();
    ids.add("player1");
    ids.add("player2");
    attribute.setGameConstructIds(ids);
    assertEquals(attribute.getGameConstructIds(), ids);
  }
}
