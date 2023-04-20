package oogasalad.view.builder.events;

import javafx.event.Event;
import javafx.event.EventType;
import oogasalad.model.constructable.Tile;

public class TileEvent extends Event {
    public static final EventType<TileEvent> ANY = new EventType<>(Event.ANY, "ANY");
    public static final EventType<TileEvent> CREATE_TILE = new EventType<>(TileEvent.ANY, "CREATE_TILE");
    public static final EventType<TileEvent> CLICK_TILE = new EventType<>(TileEvent.ANY, "CLICK_TILE");

    private Tile tile;
    public TileEvent(EventType<? extends Event> eventType, Tile tile) {
        super(eventType);
        this.tile = tile;
    }
    public Tile getTile() {
        return tile;
    }
}
