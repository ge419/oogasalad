## Strategies for Testing


- Separating UI code from logic: By separating the Card frontend code from the backend logic of purchasing and trading cards, it is possible to test the UI without any logic. This can be achieved by creating smaller classes or methods that perform specific tasks and can be easily tested in isolation.
- Using Dependency Injection: By using dependency injection, it is possible to isolate and test individual components without needing to test the entire system at once. This can be achieved by passing in mock objects or stubs for dependencies, allowing the component to be tested in isolation.
- Custom Exceptions: By using custom, descriptive exceptions, we can better plan and and detect potential bugs in our code.**


## Frontend Test Scenarios:

### Card abstract class:
1. Happy scenario - Player buys a property card: In this scenario, a player buys a property card and their balance is updated accordingly. The expected outcome is that the player's balance is decreased by the cost of the property. To test this scenario, a test case could be created that creates a new player object and initializes their balance. The test case could then create a property card object with a specified cost and call the buy() method on the card, passing in the player object. The test case could then verify that the player's balance has been decreased by the cost of the property.


2. Negative scenario - Player tries to buy a property card with insufficient funds: In this scenario, a player tries to buy a property card but does not have enough money to do so. The expected outcome is that an exception is thrown, indicating that the purchase cannot be completed. To test this scenario, a test case could be created that creates a new player object with a low balance. The test case could then create a property card object with a cost that exceeds the player's balance and call the buy() method on the card, passing in the player object. The test case could then verify that an exception is thrown, indicating that the purchase cannot be completed.


3. Happy scenario - Player draws a chance card: In this scenario, a player draws a chance card and their position is updated accordingly. The expected outcome is that the player's position is updated based on the instructions on the card. To test this scenario, a test case could be created that creates a new player object and initializes their position on the board. The test case could then create a chance card object with a specific instruction and call the execute() method on the card, passing in the player object. The test case could then verify that the player's position has been updated based on the instruction on the card.


### Tile abstract class:

1. Happy scenario - Player lands on a property tile: In this scenario, a player lands on a property tile and their position is updated accordingly. The expected outcome is that the player's position is updated to the coordinates of the property tile. To test this scenario, a test case could be created that creates a new player object and initializes their position on the board. The test case could then create a property tile object with specific coordinates and call a method to move the player to the tile. The test case could then verify that the player's position has been updated to the coordinates of the new tile.


2. Negative scenario - Player lands on a jail tile: In this scenario, a player lands on a jail tile and is unable to move for a specified number of turns. The expected outcome is that the player's position is updated to the coordinates of the jail tile, and that they are unable to move for a specified number of turns. To test this scenario, a test case could be created that creates a new player object and initializes their position on the board. The test case could then create a jail tile object with specific coordinates and call a method to move the player to the tile. The test case could then verify that the player's position has been updated to the coordinates of the new tile, and that they are unable to move for the specified number of turns.


3. Happy scenario - Tile is rendered on game board: In this scenario, a tile is rendered on the game board and its appearance is verified. The expected outcome is that the tile is rendered correctly and that its appearance matches the expected appearance. To test this scenario, a test case could be created that creates a new Tile object and renders it on a mock game board. The test case could then assert that the tile is rendered correctly and that its appearance matches the expected appearance.

### Tile interface in the GameBuilder frontend

1. Happy Scenario: Player creates a Property tile. For this case, the player selects a Property tile from the tile window and places it on the board. We can test for this by determining what tile the player just placed, and then examining the properties for that tile. If the tile is a properties tile that is initialized to it’s default values, the test is a success.


2. Negative Scenario: Player attempts to load an incorrect game file. For this case, the player clicks on the load file button, and instead of clicking on the game file that we suspect, it uploads some other file. When the code detects this, an error should be obtained from the controller, which the GameBuilder frontend should display with an error message. This test will pass if the error message appears.


### Path interface in the GameBuilder frontend:

1. Negative Scenario: Player attempts to play a game with no starting position. If the player creates tiles but never defines where the game starts, an error should be displayed at save time, notifying the player that the game is not actually playable. If a game is created that has no path but a starting position, a warning could be told to the player, though there will no error if that is what the user actually intended to do.


2. Negative Scenario: Player attempts to make a path that loops on itself. If the player makes the tiles next tile itself, there will be a warning attached to this action, but there will not be an error, as this could be intended behavior by the player for a really funky game. We can check for this by checking the next property of a tile and seeing it if it loops on itself.


3. Positive Scenario: Player makes a starting point’s next an end point. The player may do this in a branch scenario where an extremely lucky “dice” roll can result in instantly winning the game. If this happens, the code should support it as it does all other files, with no error. We can check for this just by seeing if one of the next nodes for a tile is the ending node.


## Gameplay backend:

### Player Class

1. Happy scenario - Player moves to a new position: In this scenario, a player moves to a new position on the board and their position is updated accordingly. The expected outcome is that the player's position is updated to the coordinates of the new position. To test this scenario, a test case could be created that creates a new player object and initializes their position on the board. The test case could then call a method to move the player to a new position with specific coordinates. The test case could then verify that the player's position has been updated to the coordinates of the new position.


2. Negative scenario - Player moves to an invalid position: In this scenario, a player attempts to move to an invalid position on the board and their position is not updated. The expected outcome is that the player's position remains unchanged. To test this scenario, a test case could be created that creates a new player object and initializes their position on the board. The test case could then call a method to move the player to an invalid position with specific coordinates. The test case could then verify that the player's position has not been updated.


3. Happy scenario - Player gains score points: In this scenario, a player gains score points and their score is updated accordingly. The expected outcome is that the player's score is updated to the new score. To test this scenario, a test case could be created that creates a new player object and initializes their score. The test case could then call a method to increase the player's score by a specific amount. The test case could then verify that the player's score has been updated to the new score.


### Rule/Action API:


1. Happy scenario - Player is moved on turn: We create a simple ruleset, e.g. the player chooses to move forward by 2 or 3, moves forward that amount, and the turn ends. We verify that:
   * A choice was presented with the correct options (we use a mock choice strategy)
   * The position metadata for the piece was set correctly
   * The appropriate events for passing/landing on tiles were triggered (can check this with mock rules)
   * The `END_TURN` event is triggered


2. Negative scenario - Movement action is invalid: We create a simple linear board with 3 tiles, and add an action to move forward 4 tiles. We do this test with two different invalid move strategies, canceling the move and moving to the last valid tile. We verify that:
   * An `INVALID_MOVE` event is raised
   * With the cancel strategy, no tile pass/land events are triggered
   * With the last valid tile strategy, the appropriate tile pass/land events were triggered once


3. Happy scenario - Player lands on a special tile: We create a simple “Snakes and Ladders” style board, where most tiles simply go to the next tile, while landing on one teleports you to another tile. We verify that:
   * The appropriate tile passing/landing events are generated first
   * The player is teleported to the appropriate position
   * No passing events are generated, but a landing event is generated for the teleported-to square


### Engine API


1. Happy Scenario: We can test the `run()` method with a valid set of (mock) rules. We can then mock event triggers and assert that the `Engine` should have successfully executed the game logic for the specific `Rule`(s)


2. Happy Scenario:  We can test for a specific game play case. For example, we can mock a scenario where on `OnStartTurn` a player moves forward a distance of 5, which lands on a go to jail tile; then we can assert that the `Player` has a property that indicates that they are in jail.


3. Negative Scenario: We can test the `run` method with an invalid set of inputs/invalid set of rules (e.g., missing required rules or conflicting rules). We expect that the `Engine` will throw an Exception (we will include custom exceptions once we flesh out code) that indicates that an invalid set of rules has been passed.


## Builder Backend:

### Builder class

1. Happy scenario - custom built game specifications are saved as JSON file: In this scenario, the player attempts to save the custom built game created in the Builder. The expected outcome is that the JSON file that matches the player’s design of game specifications is saved to a designated location. To test this scenario, a test case could be created that creates a JSON file based on given data. Then the test case will check on specific data if it is saved correctly on the JSON file, matching the input data and the data read from the JSON file.


2. Negative scenario - fail to save custom built game specifications as JSON file: In this scenario, the player attempts to save the custom built game created in the Builder that has invalid values. The expected outcome is that the Builder creates a JSON file with invalid data. To test this scenario, a test case could be created that creates a JSON file based on given data. Then the test case will check on specific data if it is saved correctly on the JSON file, matching the input data and the data read from the JSON file. 


3. Happy scenario - game specifications (as JSON file) are loaded into the Builder: In this scenario, the player attempts to load the existing game specifications file (JSON file) into the Builder. The expected outcome is that the JSON file is translated into a set of data classes that could be used by the frontend builder to launch the Builder interface. To test this scenario, a test case could be created that reads the selected JSON file. Then the test case will create data classes(not yet determined) that store data read from the JSON file. Then the test case can verify if the data classes match the data in the JSON file.


### Test Scenarios:


1. Happy scenario - Retrieve tile properties:
   Description: Retrieve the properties of an existing tile and verify that the data is returned correctly.
   Test case:
   Input: An existing tile object.
   Action: Retrieve the tile object's properties.
   Output: Verify that the tile object's properties are returned correctly and match the expected output.


2. Negative scenario - Retrieve properties of non-existent tile:
   Description: Attempt to retrieve the properties of a tile that does not exist and verify that an exception is thrown.
   Test case:
   Input: A tile ID that does not exist.
   Action: Attempt to retrieve the tile object's properties using the non-existent tile ID.
   Output: Verify that an exception is thrown, indicating that the tile does not exist.


### Test cases for the Tile class:


1. Happy scenario - Create a tile with valid data:
   Description: Create a tile with valid input data and verify that the tile is created successfully.
   Test case:
   Input: Valid tile ID, tile type, position, next tile ID, list of valid rules, and list of valid actions.
   Action: Create a new tile object with the input data.
   Output: Verify that the tile object is not null, and that its properties match the input data.


2. Negative scenario - Create a tile with invalid data:
   Description: Create a tile with invalid input data and verify that an exception is thrown.
   Test case:
   Input: Invalid tile ID, tile type, position, next tile ID, list of invalid rules, and list of invalid actions.
   Action: Attempt to create a new tile object with the input data.
   Output: Verify that an exception is thrown, indicating that the input data is invalid.


3. Negative scenario - Update tile properties with invalid data:
   Description: Update the properties of an existing tile with invalid input data and verify that an exception is thrown.
   Test case:
   Input: An existing tile object and new invalid input data for the tile's properties.
   Action: Attempt to update the tile object's properties with the new input data.
   Output: Verify that an exception is thrown, indicating that the new input data is invalid.




