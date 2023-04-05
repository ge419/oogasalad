### Overview:
The Tile API is designed to provide a flexible and extensible framework for creating board games. It includes a Tile class that represents a single tile on the game board, along with interfaces for rules, actions, and properties that can be associated with each tile. The API is designed to uphold SOLID principles and the Pillars of Object-Oriented Design, making it easy to extend and customize while maintaining code quality.

### Classes:

- Tile: Represents a single tile on the game board, including its position, type, and associated rules, actions, and properties.
- TileType: An enumeration of the different types of tiles that can be used in a game.
- Rule: An interface for defining rules that are associated with tiles.
- Action: An interface for defining actions that are associated with tiles.
- Properties: An interface for defining properties that are associated with tiles.
- TileProperties: A class that implements the Properties interface and provides a set of properties for a given tile.

### Example:
Here is an example of how the Tile API can be used to create a simple game board with properties:


- id: An integer identifier for the tile.
- tileType: The type of tile, represented as a TileType enumeration
- position: The position of the tile on the game board, represented as a Point object.
- nextTile: The identifier of the next tile in the sequence, or -1 if this is the last tile.
- rules: A list of Rule objects that are associated with this tile.
- actions: A list of Action objects that are associated with this tile.
- properties: An object that implements the Properties interface and provides a set of properties for this tile. 

The Properties interface defines methods for setting and getting properties of a given object. The TileProperties class implements the Properties interface and provides a set of properties for a given tile. Properties can be set and retrieved using the setProperty(String key, Object value) and getProperty(String key) methods.

### Considerations:
When designing the Tile API, we should ensure that our code is maintainable and extensible. We should also consider the performance and scalability of our game board implementation, as well as potential security risks associated with the use of user-generated rules, actions, and properties. Also, we should strive to provide clear and helpful documentation and examples to make it easy for other developers to use and extend their code.


### Assumptions
1. The Tile class is designed for use in a board game that is played on a two-dimensional game board.
2. Each tile has a unique identifier represented as an integer value.
3. Each tile has a type, represented as an enumeration of different tile types.
4. Each tile has a position on the game board, represented as a Point object.
5. Tiles are arranged in a sequential order, with each tile having a reference to the identifier of the next tile in the sequence.
6. Each tile can have one or more associated rules, represented as a list of Rule objects.
7. Each tile can have one or more associated actions, represented as a list of Action objects.
8. Each tile can have a set of properties, represented as an object that implements the Properties interface.
9. The properties for a given tile can be set and retrieved using the setProperty(String key, Object value) and getProperty(String key) methods.
10. The TileProperties class provides a default implementation of the Properties interface, but developers can create their own implementation of this interface if they want to customize the properties for their game board.
