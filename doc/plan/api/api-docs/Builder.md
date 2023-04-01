### Overview
The Builder class is designed to deal with the backend aspects of the GameBuilder.

This interface would support saving and loading the custom built game specifications to and from JSON files.

### Details
The Builder class will mainly serve as a translator between the data classes generated from the front-end Builder and JSON files that are read by the GameEngine to launch the game. This allows the users/players to save the game specifications of their own version of the game created by the Builder by creating a JSON file that stores all the necessary data. This file can be reloaded back by the GameLauncher and eventually launched by the GameEngine.

The Builder class therefore interacts closely with the Builder class from the front-end, which deals with the visual representation of the game that is being edited and stores the data on the modifications done by the user. The Builder class in the back-end will be using the data stored by the front-end Builder to create a JSON file that can be used by the GameEngine to actually launch the game.

The role of this class is straightforward, and we do not expect any extensions in terms of adding new features into this class. However, to extend additional features in necessary situations, new methods can be included to the interface to incorporate the new changes.

### Considerations
Whether the back-end Builder class should store the modifications done by the user in its own “data class” or not was one of the discussions we had while planning. We decided to keep the data stored in the front-end Builder class, and the back-end Builder class will not store any data but only process it into a JSON file for the GameEngine. This will avoid real-time complex interaction between the front-end and back-end Builder classes, and allows the back-end Builder class to focus on compiling and reading files.
