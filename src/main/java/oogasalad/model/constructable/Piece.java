package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.inject.Inject;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import oogasalad.model.attribute.ImageAttribute;
import oogasalad.model.attribute.SchemaDatabase;

public class Piece extends AbstractGameConstruct {

  public static final String SCHEMA_NAME = "piece";
  public static final String ICON_ATTRIBUTE = "icon";
  private ObjectProperty<Optional<Tile>> tile;
  private Player player;

  @Inject
  public Piece(SchemaDatabase database) {
    super(SCHEMA_NAME, database);
    tile = new SimpleObjectProperty<>(Optional.empty());
  }

  @JsonIgnore
  public ImageAttribute getImageAttribute() {
    return ImageAttribute.from(getAttribute(ICON_ATTRIBUTE).get());
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
  public ObjectProperty<Optional<Tile>> tileProperty() {
    return tile;
  }

  public ObjectProperty<Tile> concreteTileProperty() {
    Tile tile = this.tileProperty().get().get();
    return new SimpleObjectProperty<>(tile);
  }

  @JsonIgnore
  public Optional<Tile> getTile() {
    return tile.get();
  }

  @JsonIgnore
  public void setTile(Tile tile) {
    this.tile.set(Optional.ofNullable(tile));
  }

  @JsonIgnore
  public void clearTile() {
    this.tile.set(Optional.empty());
  }

  public Optional<Player> getPlayer() {
    return Optional.ofNullable(player);
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
