package oogasalad.model.engine.actions.wins;

/**
 * Interface that outlines the strategy to check for winning conditions of the game. This allows
 * flexible implementations of incorporating the multiple ways of winning the game
 *
 * @Author Jay Yoon
 */
public interface WinningConditionStrategy {

  boolean isSatisfied();
}
