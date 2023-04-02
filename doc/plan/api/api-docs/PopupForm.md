### Overview:

The PopupForm API will be used by the GameBuilder view to create popup windows for users to 
provide the information needed to create game components.

### Classes:

* PopupForm will be an interface that provides the functionality to be a popup and behave as a form to create any object
* Each object (tile, player, gamepiece, etc) will have a form class in the view that will implement this interface so a form can be created and validated properly

### Example:

For example, lets say a user wants to create a tile on the board. The PopupForm would then be passed the type Tile.
Then, using reflection, the class will figure out what fields are required and display them. When submit is pressed,
the form is validated and saved.


### Considerations:

We need to be careful how this class is created so that we can validate each form properly.
Additionally, we need to make sure to not attempt to create an input for a data type we have made an input for. 
(Example if a string is required we need an input type that can handle a string input)

### Assumptions
1. We assume that every board component that can be added by the user will have a public class
2. We assume that all of the needed variables are possible to create a form for
   * For example, the Tile should not have a css file as a parameter, since that isn't something we want the user to provide