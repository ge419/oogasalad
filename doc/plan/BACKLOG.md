## GameBuild

- **render and visualize game board**

- choose and place game elements

- setup graphical elements (background, images/colors/shapes used for game elements or board spaces

- tweak settings (point values, game timing, projectile/obstacle/object speed or number)

- **set game rules (what actions are allowed, what happens when two objects intersect, etc)**

- set interaction types and reactions (what happens when a key is pressed or the mouse is moved, without hardcoding specific keys, and what object is controlled)

- set goals for the game (how to advance within the game, how to win or lose the game)

- determine the order of advancement (what level or stage follows the current one)

- set instructions

- set splash screen

- set player setup

- set level bonuses

- allow for multiple game builder to independently run at the same time

- reset builder properties (all data cleared and start from beginning)

- **set game default turn settings**

- **save custom built game files to selected directory**

- **load an existing game file and enable changes**

- Preferences. Allow users to set preferences specific to each game played (like preferred colors, player tokens, starting configurations, key to action configurations, language, etc.)

- User clicks upload image button 

  - FileExplorer is called which displays a menu for the user to select an image
- Method in frontendbuilder class displays the image

- Loading an game existing game and editing it

    - User clicks edit game button on game menu
    - Creates a new instance of the game builder with the json file loaded in

- Saving the current game

    - User clicks the save button
    - Pass user data to “saveJSon” class to convert to a JSon.
    - FileExplorer popup window appears for user to select where the file will be saved to.

- Creating a new tile

    - User clicks a tile in the tile window
    - User clicks a location on the board image
    - Popup appears for more information on rules, aesthetics, actions, etc.

- Setting properties(?) of a tile

    - When a new tile is created, popup window for more actions/properties appears automatically.
    - To edit an existing tile, click on the tile edit button OR right click????
    - Click on the Properties option
    - Choose a property to edit.

- Defining the order of the tiles

    - Access informational window for tile
    - Click on “Movement” tab for a tile
    - Click on “Next Tile”
    - User clicks on the tile they want to move to next.

- Creating a new action for a tile

    - When a new tile is created, popup window for more actions appears automatically.
    - To edit an existing tile, click on the tile edit button OR right click????
    - Click on the Actions option
    - Choose an action to edit/add.

- Setting the aesthetics(?) of a tile

    - Access informational window for tile (edit button or automatically when making new tile)
    - Click on the “Aesthetics” tab
    - Edit the information you want to edit

- Creating a new action of a player

    - Access the player informational window
    - Go to “Player Actions”
    - Select the Player Actions that you want to use (starting money, movement, dice, etc.)
    - Edit any player actions that already exist.

- Implement Community Chest

    - User clicks the Community Chest tile
    - User places Tile on the board

- Edit Community Chest Actions

    - User clicks edit on Community Chest tile
    - Clicks on “Actions” tab
    - User selects possible actions you want for the community chest.

- Create a Railroad Tile

    - User clicks railroad tile type
    - User clicks board where to place railroad
    - User edits values in popup

- Create informational 

- Popup Window for Rule

- Popup Window for List of Actions

- Window for Tiles

- Create Path system

- Create “Teleporting” movement

- Create popup window after tile creation

- Define win conditions

- Define lose conditions

- Pick icons for players

- Keyboard Shortcuts

- Setting the size of a tile using dragging

- Implement different tile types

- Implement the Speed Die





## Game Launcher

- **show any number of available games automatically (i.e., adding a new game to play without changing any Java code)**
- **see information about each game, including its name, author, description, and image/icon**
- **select any game to play repeatedly without quitting**
- pause a game during play
- see dynamically updated game status information (i.e., heads up display (HUD))
- keep track of each game's high scores through successive runs of the program
- save their progress in the game to restart later (perhaps only at specific save points)
- play multiple, possibly different games at once
- allow users to log in, choose an avatar to be used within the game player, view personal high scores, and save their preferences
- allow different language options to be displayed


## GamePlay

- **Create a game board that includes all the necessary spaces**

- **Customize the background of game as any imported file**

- **Roll a dice that allows players to move their token around the board.**

- Establish a movement mechanism (ex. Dice, Fixed, etc) and visualize them each turn

- **Define the range of available movements (ex. Six-sided dice would be \[1,6])**

- Create a set of money with appropriate denominations and quantities.

- Design and manufacture properties that can be used to improve player-owned properties.

- **Establish the rules for each tile and each turn (specify condition and action)**

- Establish the rules for how players can acquire properties on their turn, including the process of auctioning unowned properties.

- Establish the rent amounts for each property and how they are affected by other properties.

- Develop the rules for how players can collect money from other players when they land on their properties.

- Define the rules for how players can collect money by passing "Go" on the board.

- Establish the rules for how players can draw "Chance" and "Community Chest" cards

- Establish special (change, community chest, etc) effects they have on the game.

- Develop the rules for how players can be sent to jail and how they can get out.

- Establish the rules for how players can win the game by acquiring all the properties and bankrupting all other players.

- Establish movement restrictions (disallow movement onto or past any tile with 2 pieces on it)

- Allow the player to distribute moves over multiple pieces

- **Give users the ability to include specific cheat codes for special functionality (ex. Winning the game, Restart, etc.)**

- Present the use with a list of choices, either single or multiple selection

- **Allow the player to move its token to a specific tile directly (without passing over tiles)**

- **Allow the player to move its token by passing over tiles**

    - Player is prompted to roll the die
    - Player presses the die icon, which causes the fair six-sided dice to roll and select a number
    - User rolls a double and the user displays a prompt that the user should roll again
    - The player’s piece moves the chosen number amount of spaces

- Choose a player and perform an action on them

- Remove and add money from player

- Allow the user to pick a user game piece:

    - Player is prompted to pick a game piece to represent themselves
    - Player clicks a game piece which highlights the selected piece
    - Player confirms their decision

- Allow the user to purchase property by landing on non-property

    - Player lands on a unowned property tile
    - Player is prompted to buy or auction the property
    - Player chooses the buy option, and if they have sufficient funds, they purchase the property and obtain the property card 

- Allow the auctioning process of property acquisition

    - Player lands on a unowned property tile
    - Player is prompted to buy or auction the property
    - Player chooses the auction property option
    - Banker UI prompts all Players one at a time to bid on the property until a winner is determined
    - The winner of the auction obtains the property card

- Allow the user to acquire special effects (chance/ chest card)

    - **User lands on a chest or chance card tile and earns a card**

- Allow the user to use the special effects

    - **User chooses a chance card from their pile of cards and consumes it**
    - The result is displayed on GUI

- Allow the user to go bankrupt and lose game

    - Player’s is taxed money
    - A little icon displaying the decrementing money from the user
    - The money UI shows an animation which decreases the money 
  - The player receives a prompt alerting them they have lost the game

- **Run a series of actions when landed on Start tile**

    - Player lands on GO tile
    - A little icon displays showing the player’s money incrementing 
  - The money UI shows an animation which increasing the money 

- Run a series of actions when landed on Jail tile

    - Player Lands on Go to Jail tile or Chooses a Jail Chance Card
    - A popup alerts the player of this, and they are moved to the “In Jail” tile
    - The Player’s turn ends

- Allow the player to pay tax to third party (bank or other player)

    - Player lands on Luxury Tax tile or Owned property tile
    - A popup alerts the player that they must pay tax
    - A little icon displays showing the player’s money decrementing 
  - The money UI shows an animation which decreasing the money 

- Allow the player to end the effect (ex. Get out of Jail)

    - IF the player has a get out of jail card, is prompted if they want to use it (user prompt)
    - Player rolls die

- Allow the player to collect rent from another player

    - Player lands on another player’s property
    - The amount of money they have is decremented

- Allow the player to avoid paying rent

    - Player lands on another player’s property
    - But because they have a card that allows them use the card and avoid paying rent, they are given a prompt to choose whether they want to use the card
    - If they do, remove that card and don’t decrement amount of money they have

- Allow the player to mortgage property

    - Player selects a property card they own
    - Player chooses to mortgage the property
    - The property card is moved to the mortgage pile and the player receives cash equal to half the property's value

- Allow the player to build property (houses, hotels, etc)

    - Player selects a property card they own that is not mortgaged
    - Player chooses to build houses or hotels on the property if they have sufficient funds
    - The property card is updated to reflect the new buildings and the player's funds are decreased accordingly

- Allow the player to trade with other players

    - Player chooses to initiate a trade with another player
    - Player selects the properties, cash, or cards they wish to offer in the trade
    - The other player is prompted to accept, decline, or counter-offer
    - If both players agree, the trade is completed and the exchanged properties, cash, or cards are updated accordingly.

- Save Game Data on the Web. Allow users to save process of game and load game data

- Artificial Players. Allow game designers to easily create smart enemies to oppose the player.

- User Permissions. Offer different user roles that provide different access to program functionality

- Social Center. Offer social aspects by allowing users to rate, comment, or generally collaborate while playing games.

- Game Data Viewer. All current high-profile games keep track of what happens during game play and allow it to be viewed so levels can be improved.

    - require creating a way for the game designer to choose what to measure in the game (as well as providing standard measures)
    - way to load and visualize that data in a meaningful way later, including comparing two different runs of the game.
