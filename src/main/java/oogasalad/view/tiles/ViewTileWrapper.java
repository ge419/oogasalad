package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import oogasalad.model.constructable.Tile;

/**
 * <p>ViewTileWrapper wraps around a backend tile and a view tile factory to create any
 * frontend tile as dictated by the ViewTileFactory.</p>
 * <p>Designwise this wrapper was included to prevent the use of if statements for
 * specific viewtiles. Now any viewtile can be created so long as they are present in the
 * ViewTileFactory and the map contained in the constructor of the class.</p>
 * <p><strong>Important Note:</strong> Although this class does extend ViewTile, it only
 * does so so that it can be used in place of the regular ViewTile that it wraps. It should not be
 * used as a unique ViewTile not wrappiny anything!</p>
 *
 * @author tmh85
 * @author dcm67
 */
public class ViewTileWrapper implements ViewTile {

  private final Tile myTile;
  private final TileRenderStrategy renderStrategy;
  private ViewTile myConcreteTile;
  private final Group myRoot;

  @Inject
  public ViewTileWrapper(@Assisted Tile tile, TileRenderStrategy renderStrategy) {
    this.myTile = tile;
    myTile.getHeight();
    //todo: error handle
    this.renderStrategy = renderStrategy;
    myRoot = new Group();
    bindPositionAttributes(tile);
    bindRotationAttributes(tile);
    generateTile();
    tile.viewTypeAttribute().valueProperty().addListener(
        (observable, oldValue, newValue) -> generateTile()
    );
    listenToSizeAttribute(tile.widthAttribute().valueProperty());
    listenToSizeAttribute(tile.heightAttribute().valueProperty());
  }

  private void listenToSizeAttribute(DoubleProperty sizeProperty) {
    sizeProperty.addListener(((observable, oldValue, newValue) -> {
      myConcreteTile.setSize(myTile.getWidth(), myTile.getHeight());
    }));
  }

  private void bindPositionAttributes(Tile tile) {
    myRoot.layoutXProperty().bindBidirectional(tile.positionAttribute().xProperty());
    myRoot.layoutYProperty().bindBidirectional(tile.positionAttribute().yProperty());
  }

  private void bindRotationAttributes(Tile tile) {
    myRoot.rotateProperty().bindBidirectional(tile.positionAttribute().angleProperty());
  }


  private void generateTile() {
    myConcreteTile = renderStrategy.createViewTile(myTile);
    myRoot.getChildren().setAll(myConcreteTile.asNode());
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
    myConcreteTile.setSize(width, height);
  }

  @Override
  public Node asNode() {
//    return myConcreteTile.asNode();
    return myRoot;
  }
}
