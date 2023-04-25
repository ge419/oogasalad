package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.inject.Inject;
import java.util.Optional;
import oogasalad.model.attribute.ImageAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileAttribute;

public class Piece extends AbstractGameConstruct {

  public static final String SCHEMA_NAME = "piece";
  public static final String ICON_ATTRIBUTE = "icon";
  public static final String TILE_ATTRIBUTE = "tile";
  private Player player;

  @Inject
  public Piece(SchemaDatabase database) {
    super(SCHEMA_NAME, database);
  }

  @JsonIgnore
  public ImageAttribute getImageAttribute() {
    return ImageAttribute.from(getAttribute(ICON_ATTRIBUTE).get());
  }

  @JsonIgnore
  public TileAttribute getTileAttribute() {
    return TileAttribute.from(getAttribute(TILE_ATTRIBUTE).get());
  }

  @JsonIgnore
  public String getImage() {
    return getImageAttribute().getValue();
  }

  @JsonIgnore
  public void setImage(String image) {
    getImageAttribute().setValue(image);
  }

  @JsonIgnore
  public Optional<String> getTileId() {
    return getTileAttribute().getId();
  }

  @JsonIgnore
  public void setTileId(String id) {
    getTileAttribute().setId(id);
  }

  public Optional<Player> getPlayer() {
    return Optional.ofNullable(player);
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
