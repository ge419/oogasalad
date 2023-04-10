package oogasalad.view.builder.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import oogasalad.view.builder.panefeatures.DraggerAPI;

/**
 * NodeStorer will hold information related to each node that cannot be stored in the node itself.
 *
 * @author tmh85
 */
public class NodeStorer {

  private Map<String, Node> myMap;
  private List<DraggerAPI> myDraggers;

  public NodeStorer() {
    myMap = new HashMap<>();
    myDraggers = new ArrayList<>();
  }

  /**
   * Add a node to the NodeStorer with an id that can be used to retrieve it later.
   *
   * @param node desired node to save
   * @param id   desired id to use to restore
   */
  public void addNode(Node node, String id) {
    myMap.put(id, node);

  }

  /**
   * Retrieve a node using the ID provided to the storer earlier.
   *
   * @param id id of the node you want
   * @return node
   */
  public Node getNodeWithString(String id) {
    return myMap.get(id);
  }

  public void addDragger(DraggerAPI dragger) {
    myDraggers.add(dragger);
  }

  public List<DraggerAPI> getDraggers() {
    return myDraggers;
  }
}
