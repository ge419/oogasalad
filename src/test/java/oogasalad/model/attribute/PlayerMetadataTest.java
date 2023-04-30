package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerMetadataTest {

  private PlayerAttribute playerAttribute;
  private PlayerMetadata playerMetadata;
  private static final String KEY = "playerKey";

  @BeforeEach
  public void setUp() {
    playerAttribute = mock(PlayerAttribute.class);
    playerMetadata = new PlayerMetadata(KEY);
  }

  @Test
  public void testMakeAttribute() {
    Attribute attribute = playerMetadata.makeAttribute();
    assertTrue(attribute instanceof PlayerAttribute);
    assertEquals(KEY, attribute.getKey());
    assertNotNull(PlayerAttribute.from(attribute).getId());
  }

}