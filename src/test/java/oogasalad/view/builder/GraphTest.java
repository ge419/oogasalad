//package oogasalad.view.builder;
//
//import oogasalad.view.Coordinate;
//import oogasalad.view.builder.graphs.Graph;
//import oogasalad.view.tiles.BasicTile;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GraphTest extends DukeApplicationTest {
//
//  Graph myGraph;
//  BasicTile myBasic;
//  @BeforeEach
//  void setUp(){
//    myGraph = new Graph();
//    BasicTile basic = new BasicTile(0, new Coordinate(10, 20));
//    myGraph.addTile(basic);
//  }
//
//  @Test
//  void testAddTile(){
//    BasicTile basic = new BasicTile(0, new Coordinate(10, 20));
//    myBasic = basic;
//    myGraph.addTile(basic);
//
//    assertEquals(myGraph.getTiles().get(0).getTileId(), 0);
//  }
//
//  @Test
//  void testAddNext(){
//    BasicTile basic2 = new BasicTile(1, new Coordinate(10, 20));
//    BasicTile basic3 = new BasicTile(2, new Coordinate(10, 20));
//    myGraph.addTileNext(myBasic, basic2);
//    myGraph.addTileNext(myBasic, basic3);
//
//    // Test adding the same tiles as a next and seeing if it inflates the graph.
//    myGraph.addTileNext(myBasic, basic2);
//    myGraph.addTileNext(myBasic, basic3);
//
//    assertEquals(myGraph.getNextTiles(myBasic).size(), 2);
//    assertEquals(myGraph.getNextTiles(myBasic).get(0).getTileId(), 1);
//    assertEquals(myGraph.getNextTiles(myBasic).get(1).getTileId(), 2);
//  }
//}
