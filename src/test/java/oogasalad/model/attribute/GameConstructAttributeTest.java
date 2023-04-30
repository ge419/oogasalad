package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameConstructAttributeTest {

  private GameConstructAttribute gameConstructAttribute;
  private static final String ID = "test_id";
  private static final String NEW_ID = "new_id";


  @BeforeEach
  public void setUp() {
    gameConstructAttribute = new GameConstructAttributeImpl("test_key", "test_id");
  }

  @Test
  public void testFrom() {
    Attribute attribute = GameConstructAttribute.from(gameConstructAttribute);
    assertEquals(gameConstructAttribute, attribute);
  }

  @Test
  public void testIdProperty() {
    ObjectProperty<Optional<String>> idProperty = gameConstructAttribute.idProperty();
    assertEquals(gameConstructAttribute.getId(), idProperty.get());

    idProperty.set(Optional.of(NEW_ID));
    assertEquals(gameConstructAttribute.getId().get(), idProperty.get().get());
  }

  @Test
  public void testGetId() {
    Optional<String> id = gameConstructAttribute.getId();
    assertTrue(id.isPresent());
    assertEquals(id.get(), ID);

    gameConstructAttribute.setId("");
    id = gameConstructAttribute.getId();
    assertFalse(id.isPresent());
  }

  @Test
  public void testGetUncheckedId() {
    String uncheckedId = gameConstructAttribute.getUncheckedId();
    assertEquals(uncheckedId, ID);

    gameConstructAttribute.setId("");
    uncheckedId = gameConstructAttribute.getUncheckedId();
    assertEquals(uncheckedId, "");
  }

  @Test
  public void testSetId() {
    gameConstructAttribute.setId(NEW_ID);
    Optional<String> id = gameConstructAttribute.idProperty().get();
    assertTrue(id.isPresent());
    assertEquals(id.get(), NEW_ID);

    gameConstructAttribute.setId("");
    id = gameConstructAttribute.idProperty().get();
    assertFalse(id.isPresent());
  }

  private static class GameConstructAttributeImpl extends GameConstructAttribute {

    public GameConstructAttributeImpl(String key, String id) {
      super(key, id);
    }

  }
}