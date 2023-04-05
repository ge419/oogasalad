### Design Overview

The API should allow developers to create new types of game pieces with unique behaviors, abilities, and appearance. It should provide high-level abstractions for game pieces that shield developers from low-level details like rendering, animation, or input handling. Finally it will follow consistent naming conventions, interfaces, and design patterns, to make it easy to learn and use.

****

### Classes

- PlayerPiece class: will represent the movable piece that will represent a player in the game

- AssetPiece Abstraction: a piece that the player will be able to buy, sell, and own 

  - House Piece
- Hotel Piece

- CurrencyPiece: a piece that will represent the currency of the game which will allow the players to purchase items in the game

- Exceptions:

    - Entering invalid price for money or property value

****

### Example

Task: Add a new type of game piece with unique abilities to the game.

Solution: To add a new type of game piece with unique abilities, we would need to create a new subclass of the Piece interface that implements the desired behaviors. For example, let's say we want to add a "Horse" game piece to the game of Monopoly that represents a movable piece.

### Details

The GamePiece API would create and give important properties (value, type, etc) to each instance of this API. It would be called in a GameBoard class and would be utilized to represent the player within the game. It would also interact with the Tile class where if the piece landed on a specific Tile with a condition, an Effect would occur. 

****

### Considerations

An issue that would need to be resolved is the issue with this API being totally usable for different games. We would need to see every game we want to develop and make sure the methods in this API are applicable. The ambiguity as of right now is what will be considered a GamePiece (other than what was listed above).