//package oogasalad.view.builder.gameholder;
//
//import java.util.List;
//import java.util.Optional;
//import oogasalad.model.constructable.BBoard;
//import oogasalad.model.constructable.Player;
//import oogasalad.view.builder.board.ImmutableBoardInfo;
//import oogasalad.view.builder.graphs.ImmutableGraph;
//
//
//public class GameHolder implements GameInterface, MutableGameInterface {
//
//  ImmutableGraph myGraph;
//  ImmutableBoardInfo myBoardInfo;
//  BBoard board;
//  Optional<List<Player>> players;
//  // Optional<List<Card>> cards;
//
//  public GameHolder(ImmutableGraph myGraph, ImmutableBoardInfo myBoardInfo, BBoard board,
//      Optional<List<Player>> players) {
//    this.myGraph = myGraph;
//    this.myBoardInfo = myBoardInfo;
//    this.board = board;
//    this.players = players;
//  }
//
//  public GameHolder() {
//    //myGraph = new Graph();
//  }
//
//  @Override
//  public ImmutableGraph getTileGraph() {
//    return myGraph;
//  }
//  @Override
//  public void setTileGraph(ImmutableGraph graph) {
//    myGraph = graph;
//  }
//
//  @Override
//  public ImmutableBoardInfo getBoardInfo() {
//    return myBoardInfo;
//  }
//
//  @Override
//  public void setBoardInfo(ImmutableBoardInfo board) {
//    myBoardInfo = board;
//  }
//
//  public ImmutableBoardInfo getMyBoardInfo() {
//    return myBoardInfo;
//  }
//  public BBoard getBoard() {
//    return board;
//  }
//  public Optional<List<Player>> getPlayers() {
//    return players;
//  }
//}
