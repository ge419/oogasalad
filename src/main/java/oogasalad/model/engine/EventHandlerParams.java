package oogasalad.model.engine;

/**
 * Parameters given to an {@link EventHandler}.
 *
 * @param event       the triggered event
 * @param actionQueue used to register new events
 * @author Dominic Martinez
 */
public record EventHandlerParams<T extends Event<T>>(T event, ActionQueue actionQueue) {

}