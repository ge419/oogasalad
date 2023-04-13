//package oogasalad.model.engine.rules;
//
//import javax.inject.Inject;
//import javax.inject.Provider;
//import oogasalad.model.engine.EventHandlerParams;
//import oogasalad.model.engine.EventRegistrar;
//import oogasalad.model.engine.actions.RollDieAndMoveAction;
//import oogasalad.model.engine.events.MonopolyEvent;
//
//public class DieRule implements Rule {
//
//  private final Provider<RollDieAndMoveAction> dieActionProvider;
//
//  @Inject
//  public DieRule(Provider<RollDieAndMoveAction> dieActionProvider) {
//    this.dieActionProvider = dieActionProvider;
//  }
//
//  @Override
//  public void registerEventHandlers(EventRegistrar registrar) {
//    registrar.registerHandler(MonopolyEvent.START_TURN, this::rollDie);
//  }
//
//  private void rollDie(EventHandlerParams eventHandlerParams) {
//    eventHandlerParams.actionQueue().add(1, dieActionProvider.get());
//  }
//}
