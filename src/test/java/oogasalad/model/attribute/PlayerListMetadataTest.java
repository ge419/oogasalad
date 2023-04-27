package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerListMetadataTest {

  private PlayerListMetadata playerMetadata;
  private PlayerListAttribute listAttribute;
  private static final String KEY = "playerKey";

  @BeforeEach
  public void setUp() {
    listAttribute = mock(PlayerListAttribute.class);
    playerMetadata = new PlayerListMetadata(KEY);
  }

  @Test
  public void testMakeAttribute() {
    Attribute attribute = playerMetadata.makeAttribute();
    assertTrue(attribute instanceof PlayerListAttribute);
    assertEquals(attribute.getKey(), KEY);
    assertEquals(0, ((PlayerListAttribute) attribute).getGameConstructIds().size());
  }

}