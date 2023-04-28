package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.Map;
import java.util.function.BiFunction;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import oogasalad.model.constructable.Tile;

public class SimpleViewTile implements ViewTile {

  private final Tile myTile;
  private final ViewTileFactory myTileFactory;
  private Map<String, BiFunction<ViewTileFactory, Tile, ViewTile>> myMap;
  private ViewTile myConcreteTile;
  private Group myRoot;

  @Inject
  public SimpleViewTile(@Assisted Tile tile, ViewTileFactory tileFactory) {
    this.myTile = tile;
    myTile.getHeight();
    //todo: error handle
    myMap = Map.of(
        "image", (factory, btile) -> factory.createImageTile(btile),
        "basic", (factory, btile)-> factory.createBasicTile(btile),
        "street", (factory, btile)-> factory.createStreetTile(btile),
        "custom", (factory, btile)-> factory.createCustomTile(btile)
    );
    this.myTileFactory = tileFactory;
    myRoot = new Group();
    myRoot.layoutXProperty().bindBidirectional(tile.positionAttribute().xProperty());
    myRoot.layoutYProperty().bindBidirectional(tile.positionAttribute().yProperty());
    myRoot.rotateProperty().bindBidirectional(tile.positionAttribute().angleProperty());
    generateTile();
    tile.viewTypeAttribute().valueProperty().addListener(
        (observable, oldValue, newValue) -> generateTile()
    );

//    myRoot.boundsInLocalProperty().addListener(((observable, oldValue, newValue) -> {
//      tile.setWidth(newValue.getWidth());
//      tile.setHeight(newValue.getHeight());
//    }));

    listenToSizeAttribute(tile.widthAttribute().valueProperty());
    listenToSizeAttribute(tile.heightAttribute().valueProperty());

  }

  private void listenToSizeAttribute(DoubleProperty sizeProperty){
    sizeProperty.addListener(((observable, oldValue, newValue) -> {
      myConcreteTile.setSize(myTile.getWidth(), myTile.getHeight());
    }));
  }


  private void generateTile() {
    myConcreteTile = createConcreteTile(myTile.getViewType(), myTile);
    myRoot.getChildren().setAll(myConcreteTile.asNode());
  }

  public ViewTile createConcreteTile(String type, Tile tile) {
    System.out.println(type);
    System.out.println(tile);
    return myMap.get(type).apply(myTileFactory, tile);
  }

  @Override
  public String getTileId() {
    return myTile.getId();
  }

  @Override
  public Tile getTile() {
    return myTile;
  }

  @Override
  public void setSize(double width, double height) {
    myRoot.resize(width, height);
  }

  @Override
  public Node asNode() {
    return myRoot;
  }
}
