# Event loop design

## Overview

The behavior of the game is defined by `Rule`s. A rule subscribes to events, such as the start of a
turn or landing on a tile. Rules do not change the game state on their own. Instead, rules
add `Action`s to the `ActionQueue`. The `ActionQueue` is a priority queue that processes actions,
such as move forward N spaces or set property X, in order. Actions change the current state, and
also trigger events, which in turn run the rules that subscribed to them, and so forth.

Each player has their own `ActionQueue`, allowing them to process actions independently. In a   
turn-based game generally only the current player's queue is used, but some mechanics
(e.g. trading, auctioning) will interact with multiple players at once.

## Classes

- Action: represents a specific action (e.g. move 4 spaces)
- ActionQueue: per player priority queue of actions
- Event: represents a specific event, consisting of a type and additional metadata
- EventEmitter: allows actions to emit events
- EventHandler: functional interface for rule event handlers
- EventRegistrar: allows rules to subscribe to event types
- EventType: represents a specific type of event
- Rule: abstracts away game-specific behavior

## Example

Rules encapsulate all game behavior. For instance, the behavior of a turn based game might be
represented by the following rules:

```java
enum TurnEvent implements EventType {
  START_TURN("start-turn"), END_TURN("end-turn");

  private final String type;

  TurnEvent(String type) {
    this.type = type;
  }

  public String type() {
    return type;
  }
}

class StartTurnRule {

  public void registerEventHandlers(EventHandler emitter) {
    emitter.apply(TurnEvent.START_TURN, this::onTurnStart);
  }

  private void onTurnStart(Event e, ActionQueue actionQueue) {
    // Has the lowest priority among actions in this game
    actionQueue.add(new EventAction(END_TURN), 10);
  }
}

class EndTurnRule {

  public void registerEventHandlers(EventHandler emitter) {
    emitter.apply(TurnEvent.END_TURN, this::onTurnEnd);
  }

  private void onTurnEnd(Event e, ActionQueue actionQueue) {
    // Actions with the same priority execute in the order they were added
    actionQueue.add(new SetMetadataAction(CURRENT_PLAYER, new PlayerMetadata(nextPlayer(e))), 1);
    actionQueue.add(new EventAction(START_TURN), 1);
  }
}
```

## Details

The rules are edited by the frontend via two methods:
- Preset rules can be selected. We will make these for rules that are not yet expressible via our UI.
- Rules can be generated via the UI. A simple example would be providing the user a list of predicates, and letting them make rules of the form `if X then ACTION`.

The UI also subscribes to events via `EventHandler`, so the frontend can animate any event happening in-game.

## Considerations

### Extensibility

This system is designed to be highly extensible, and the behavior of the game can be changed with
high granularity by modifying the list of rules. However, there is a cost: compile-time safety.
To allow the available metadata/events to be extensible to the user, they are represented by Strings.
We also cannot directly tie an event to a certain metadata class, since the available events are
not known at compile-time.

### Data structure

The exact structure of game data is not yet worked out. The API may need to be adjusted slightly to
accept the state of the game as an argument.

