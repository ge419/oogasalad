## Sprint 1 (~9 days) :
#### Creation of simple roll to reach the end game. GameBuilder allows for creation of this game, GameEngine can read files and play them. Minimal GameLauncher.

- render and visualize game board
- set game rules (what actions are allowed, what happens when two objects intersect, etc)
- set goals for the game (how to advance within the game, how to win or lose the game)
- set game default turn settings 
- Defining the order of the tiles
- load an existing game file and enable changes
- show any number of available games automatically (i.e., adding a new game to play without changing any Java code)
- see information about each game, including its name, author, description, and image/icon
- select any game to play repeatedly without quitting
- Create a game board that includes all the necessary spaces
- Customize the background of game as any imported file
- Roll a dice that allows players to move their token around the board
- Define the range of available movements (ex. Six-sided dice would be \[1,6])
- Establish the rules for each tile and each turn (specify condition and action)
- Give users the ability to include specific cheat codes for special functionality (ex. Winning the game, Restart, etc.)
- Allow the player to move its token to a specific tile directly (without passing over tiles)
- Allow the player to move its token by passing over tiles
- Run a series of actions when landed on Start tile


## Sprint 2 (~5 days):
#### Further development of features to make it more robust. GameBuilder creates groups of rules to allow for similar properties. Initial implementation of Monopoly with most features.

- choose and place game elements
- setup graphical elements (background, images/colors/shapes used for game elements or board spaces
- tweak settings (point values, game timing, projectile/obstacle/object speed or number)
- set instructions
- set player setup
- reset builder properties (all data cleared and start from beginning)
- Loading an game existing game and editing it
- Creating a new tile
- Creating a new action for a tile
- Creating a new action of a player
- Implement Community Chest
- Establish a movement mechanism (ex. Dice, Fixed, etc) and visualize them each turn
- Define the rules for how players can collect money by passing "Go" on the board.
- Establish the rules for how players can draw "Chance" and "Community Chest" cards
- Establish special (change, community chest, etc) effects they have on the game.
- Develop the rules for how players can be sent to jail and how they can get out.
- Choose a player and perform an action on them
- Remove and add money from player
- Allow the user to acquire special effects (chance/ chest card)
- Allow the user to use the special effects
- Run a series of actions when landed on Jail tile
- Allow the player to end the effect (ex. Get out of Jail)

****


## Sprint 3 (7 days) :
#### Minor refactoring. Full completion of basic Monopoly and game building features completed. Random Players. Creation of Mod Game.

- Preferences. Allow users to set preferences specific to each game played (like preferred colors, player tokens, starting configurations, key to action configurations, language, etc.)
- User clicks upload image button 
- Saving the current game
- Setting properties(?) of a tile
- Edit Community Chest Actions
- pause a game during play
- save their progress in the game to restart later (perhaps only at specific save points)
- Create a set of money with appropriate denominations and quantities.
- Establish the rent amounts for each property and how they are affected by other properties.
- Develop the rules for how players can collect money from other players when they land on their properties.
- Establish the rules for how players can win the game by acquiring all the properties and bankrupting all other players.
- Establish movement restrictions (disallow movement onto or past any tile with 2 pieces on it)
- Present the use with a list of choices, either single or multiple selection
- Allow the user to pick a user game piece:
- Allow the user to purchase property by landing on non-property
- Allow the user to go bankrupt and lose game
- Allow the player to pay tax to third party (bank or other player)
- Allow the player to collect rent from another player
- Allow the player to avoid paying rent
- Allow the player to mortgage property
- Allow the player to build property (houses, hotels, etc)
- Allow the player to trade with other players

****


## Sprint 4 (rest of project):
#### Refactor Code, add extensions (Smart AI players, Networking, Preferences), add additional games

- determine the order of advancement (what level or stage follows the current one)
- set splash screen
- set level bonuses
- allow for multiple game builder to independently run at the same time
- Setting the aesthetics of a tile
- keep track of each game's high scores through successive runs of the program
- see dynamically updated game status information (i.e., heads up display (HUD))
- play multiple, possibly different games at once
- allow different language options to be displayed
- Establish the rules for how players can acquire properties on their turn, including the process of auctioning unowned properties.
- Save Game Data on the Web. Allow users to save process of game and load game data
- Artificial Players. Allow game designers to easily create smart enemies to oppose the player.
- Social Center. Offer social aspects by allowing users to rate, comment, or generally collaborate while playing games.

****


## Possible Extensions

- Save Game Data on the Web. Allow users to save process of game and load game data

- Artificial Players. Allow game designers to easily create smart enemies to oppose the player.

- User Permissions. Offer different user roles that provide different access to program functionality

- Social Center. Offer social aspects by allowing users to rate, comment, or generally collaborate while playing games.

- Game Data Viewer. All current high-profile games keep track of what happens during game play and allow it to be viewed so levels can be improved.

    - require creating a way for the game designer to choose what to measure in the game (as well as providing standard measures)
    - way to load and visualize that data in a meaningful way later, including comparing two different runs of the game.

- Preferences. Allow users to set preferences specific to each game played (like preferred colors, player tokens, starting configurations, key to action configurations, language, etc.)

****


## Team Responsibilities:

- Trevon: mainly responsible for GameBuilder Frontend API, along with subclasses of the GameBuilder (such as tiles)
- Jason: mainly responsible for GameBuilder Frontend API, along with subclasses of the GameBuilder (such as paths)
- Jay: mainly responsible for GameEngine Backend API (Engine APIs, Rule and Action classes)
- Chika: mainly responsible for GameEngine Backend API (Dataclasses, Engine APIs)
- Dominic: mainly responsible for GameEngine Backend API (Rule, Action, and Choice APIs)
- Changmin: mainly responsible for GameBuilder Backend API (Builder APIs)
- Woongyu: mainly responsible for GameEngine Frontend API, Controller
- Matthew: mainly responsible for Controller (communication between frontend backend in Game Engine)
- Aloye: mainly responsible for GameEngine Frontend API
- Nathaniel: mainly responsible for GameBuilder Backend API (Builder APIs)
