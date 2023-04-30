package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.ColorAttribute;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.PlayerAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.view.Coordinate;

public class Tile extends AbstractGameConstruct {

  public static final String BASE_SCHEMA_NAME = "tile";
  public static final String VIEW_TYPE_ATTRIBUTE = "type";
  public static final String NEXT_ATTRIBUTE = "next";
  public static final String POSITION_ATTRIBUTE = "position";
  public static final String WIDTH_ATTRIBUTE = "width";
  public static final String HEIGHT_ATTRIBUTE = "height";
  public static final String INFO_ATTRIBUTE = "info";
  public static final String PRICE_ATTRIBUTE = "price";
  public static final String COLOR_ATTRIBUTE = "color";
  public static final String OWNED_ATTRIBUTE = "owned";

  @Inject
  public Tile(@JacksonInject SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
  }

  @JsonIgnore
  public TileListAttribute nextAttribute() {
    return TileListAttribute.from(getAttribute(NEXT_ATTRIBUTE).get());
  }

  @JsonIgnore
  public BooleanAttribute ownedAttribute() {
    return BooleanAttribute.from(getAttribute(OWNED_ATTRIBUTE).get());
  }

  @JsonIgnore
  public PlayerAttribute ownerAttribute() {
    return PlayerAttribute.from(getAttribute("owner").get());
  }

  @JsonIgnore
  public String getOwnerId() {
    return ownerAttribute().getIdValue();
  }

  @JsonIgnore
  public void setOwnerId(String id) {
    ownerAttribute().setIdValue(id);
  }

  @JsonIgnore
  public boolean isOwned() {
    return ownedAttribute().getValue();
  }

  @JsonIgnore
  public void setOwned() {
    ownedAttribute().setValue(true);
  }

  @JsonIgnore
  public List<String> getNextTileIds() {
    return nextAttribute().getTileIds();
  }

  @JsonIgnore
  public PositionAttribute positionAttribute() {
    return PositionAttribute.from(getAttribute(POSITION_ATTRIBUTE).get());
  }

  @JsonIgnore
  public StringAttribute viewTypeAttribute() {
    return StringAttribute.from(getAttribute(VIEW_TYPE_ATTRIBUTE).get());
  }

  @JsonIgnore
  public Coordinate getCoordinate() {
    return positionAttribute().getCoordinate();
  }

  @JsonIgnore
  public void setCoordinate(Coordinate coordinate) {
    positionAttribute().setCoordinate(coordinate);
  }

  @JsonIgnore
  public double getX() {
    return getCoordinate().getXCoor();
  }

  @JsonIgnore
  public void setX(double x) {
    positionAttribute().setX(x);
  }

  @JsonIgnore
  public double getY() {
    return getCoordinate().getYCoor();
  }

  @JsonIgnore
  public void setY(double y) {
    positionAttribute().setY(y);
  }

  @JsonIgnore
  public DoubleAttribute widthAttribute() {
    return DoubleAttribute.from(getAttribute(WIDTH_ATTRIBUTE).get());
  }

  @JsonIgnore
  public DoubleAttribute heightAttribute() {
    return DoubleAttribute.from(getAttribute(HEIGHT_ATTRIBUTE).get());
  }

  @JsonIgnore
  public double getWidth() {
    return widthAttribute().getValue();
  }

  @JsonIgnore
  public double getHeight() {
    return heightAttribute().getValue();
  }

  @JsonIgnore
  public void setWidth(double width) {
    widthAttribute().setValue(width);
  }

  @JsonIgnore
  public void setHeight(double height) {
    heightAttribute().setValue(height);
  }

  @JsonIgnore
  public ColorAttribute colorAttribute(){
    return ColorAttribute.from(getAttribute(COLOR_ATTRIBUTE).get());
  }

  @JsonIgnore
  public String getInfo() {
    return StringAttribute.from(getAttribute(INFO_ATTRIBUTE).get()).getValue();
  }

//  @JsonIgnore
//  public String getPrice() {
//    return Double.toString(DoubleAttribute.from(getAttribute("price").get()).getValue());
//  }

  @JsonIgnore
  public String getViewType() {
    return viewTypeAttribute().getValue();
  }

  @JsonIgnore
  public DoubleAttribute getPriceAttribute() {
    return DoubleAttribute.from(getAttribute(PRICE_ATTRIBUTE).get());
  }

  @JsonIgnore
  public Double getPrice() {
    return getPriceAttribute().getValue();
  }

  @JsonIgnore
  public void setPrice(double newPrice) {
    getPriceAttribute().setValue(newPrice);
  }
}

