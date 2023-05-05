package oogasalad.view.builder.events;

import javafx.event.Event;
import javafx.event.EventType;
import oogasalad.model.constructable.Tile;
import oogasalad.view.tiles.ViewTile;

/**
 * A custom Event type used to track events related to ViewTiles in the builder. These events are
 * fired when events related to tiles happen, and filters/handlers can be used to run methods when
 * these events happen Example Usage: Firing an event: TileEvent event = new
 * TileEvent(TileEvent.DELETE_TILE, tile); myPane.fireEvent(event); Calling a method when an event
 * is fired: myPane.addEventFilter(TileEvent.DELETE_TILE, this::deleteTile);
 *
 * @author Jason Fitzpatrick
 */
public class TileEvent extends Event {

  public static final EventType<TileEvent> DELETE_TILE = new EventType<>(TileEvent.ANY,
      "DELETE_TILE");
  public static final EventType<TileEvent> SET_NEXT_TILE = new EventType<>(TileEvent.ANY,
      "SET_NEXT_TILE");
  public static final EventType<TileEvent> DELETE_NEXT_TILE = new EventType<>(TileEvent.ANY,
      "DELETE_NEXT_TILE");
  public static final EventType<TileEvent> SHOW_FORM = new EventType<>(TileEvent.ANY, "SHOW_FORM");
  public static final EventType<TileEvent> SELECT_TILE = new EventType<>(TileEvent.ANY,
      "SELECT_TILE");
  public static final EventType<TileEvent> DRAG_TILE = new EventType<>(TileEvent.ANY, "DRAG_TILE");
  private final ViewTile tile;

  /**
   * Creates a new instance of a TileEvent, with the specified type. This behaves like any other
   * JavaFX event and can be fired and handled in the same way
   *
   * @param eventType EventType such as DELETE_TILE or SET_NEXT_TILE that specifies what type of
   *                  TileEvent should be created
   * @param tile      ViewTile
   */
  public TileEvent(EventType<? extends Event> eventType, ViewTile tile) {
    super(eventType);
    this.tile = tile;
  }

  /**
   * Returns the ViewTile relevant to the event
   *
   * @return ViewTile
   */
  public ViewTile getViewTile() {
    return tile;
  }

  /**
   * Returns the backend Tile relevant to the event
   *
   * @return Tile
   */
  public Tile getTile() {
    return tile.getTile();
  }
}
