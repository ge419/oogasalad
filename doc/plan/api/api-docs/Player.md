### Overview
- The Player class is designed to represent a player in a board game.
- This interface would define the basic properties and methods that every player in the game would need to have. It would include methods such as getting and setting the player's name, checking if the player is active, and getting and setting the player's current location on the board.
- It is designed to encapsulate the data and methods related to a player, providing a clear and simple interface for other developers to interact with. This encapsulation promotes modularity and ability to handle changes in data structures without affecting functionality of the program.

### Details
The Player class in the board game program handles specific features related to a player's actions during the game. This includes moving their game piece on the board, rolling dice to determine movement, and interacting with game spaces such as buying property or paying rent. The Player class may use resources such as a game board object, a dice object, and a list of owned properties.

The Player class collaborates with other APIs in the board game program, such as the Board and Property classes. For example, when a player lands on a property space, the Property class may be called to handle the necessary actions such as allowing the player to buy the property or paying rent to the property's owner. The Player class may also call on the Board class to determine the player's current location and possible moves.

To extend the Player class to include additional requirements, such as implementing a new action for the player, a new method can be added to the interface. For example, if a "trade" action is added to the game, a new method called "trade" can be added to the Player interface. Then, the concrete implementation of the Player class can be updated to include the necessary functionality for the trade action.


### Classes and Examples
```located in ./Player.java file```

### Considerations

Assumptions
* there is a game board and that the player's movements are limited to the positions on the board.
* the game is turn-based and that each player takes turns to make their moves.
* the game has a finite number of rounds or a defined end condition.
* the player's moves are validated by the game rules, and it does not perform any validation checks on its own.
* the player has a name and a unique identifier, and that these values are set when the player is created.
* the game can be played with two or more players, and that each player has their own Player object.

Design Decisions
1. Player identification: How will the players be identified? Will each player have a unique ID, or will they be identified by their name or some other attribute?
   If each player is identified by a unique ID, it may make it easier to manage the player's actions throughout the game. However, if the ID is not easily understandable for players, it may lead to confusion.

2. Game turn management: How will the game turns be managed? Will the Player class be responsible for managing the turn order, or will that be the responsibility of another class or API?
   Similarly, if the Player class is responsible for managing the turn order, it may simplify the game logic. However, if the game logic is complex, it may make more sense to have a separate class or API to manage the game turns.

