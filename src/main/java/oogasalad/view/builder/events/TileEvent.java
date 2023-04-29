package oogasalad.view.builder.events;

import javafx.event.Event;
import javafx.event.EventType;
import oogasalad.model.constructable.Tile;
import oogasalad.view.tiles.ViewTile;

public class TileEvent extends Event {
    //public static final EventType<TileEvent> ANY = new EventType<>(Event.ANY, "ANY");
    public static final EventType<TileEvent> DELETE_TILE = new EventType<>(TileEvent.ANY, "DELETE_TILE");
    public static final EventType<TileEvent> SET_NEXT_TILE = new EventType<>(TileEvent.ANY, "SET_NEXT_TILE");
    public static final EventType<TileEvent> SHOW_FORM = new EventType<>(TileEvent.ANY, "SHOW_FORM");
    public static final EventType<TileEvent> SELECT_TILE = new EventType<>(TileEvent.ANY, "SELECT_TILE");
    public static final EventType<TileEvent> DRAG_TILE = new EventType<>(TileEvent.ANY, "DRAG_TILE");

    private ViewTile tile;
    public TileEvent(EventType<? extends Event> eventType, ViewTile tile) {
        super(eventType);
        this.tile = tile;
    }
    public ViewTile getViewTile() {
        return tile;
    }
    public Tile getTile() {
        return tile.getTile();
    }
}
