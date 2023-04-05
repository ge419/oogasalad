package gameplay_frontend;

/**
 An interface representing frontend components that display and can be updated.
 This interface defines the properties and behavior of the component, including the methods for
 updating the amount displayed.
 <p>
 Implementation details:
 <p>
 setAmount(int amount) - This method sets the amount displayed by the component to the
 specified value.
 <p>
 addAmount(int amount) - This method adds the specified amount displayed by the component.
 <p>
 subtractAmount(int amount) - This method subtracts the specified amount from the current amount
 displayed by the component.
 <p>
 These methods allow the game logic to update the amount displayed by the component
 dynamically without needing to know the specifics of the implementation. Any class that
 implements this interface should provide these methods to allow for easy manipulation of the
 component's state.
 */

public interface Mutable {
  public void setAmount(int amount);
  public void addAmount(int amount);
  public void subtractAmount(int amount);

}
