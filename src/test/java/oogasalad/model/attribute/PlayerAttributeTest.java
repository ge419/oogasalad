package oogasalad.model.attribute;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class PlayerAttributeTest {

  private static final String KEY = "player";
  private static final String ID = "id_0001";
  private PlayerAttribute playerAttr;

  @Before
  public void setUp() {
    playerAttr = new PlayerAttribute(KEY, ID);
  }

  @Test
  public void testGetKey() {
    assertEquals(KEY, playerAttr.getKey());
  }

  @Test
  public void testGetId() {
    assertEquals(ID, playerAttr.getId().get());
  }

  @Test
  public void testFrom() {
    Attribute attribute = PlayerAttribute.from(playerAttr);
    assertEquals(playerAttr, attribute);
  }
}
