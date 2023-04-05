1. Allow the player to move its token by passing over tiles
- Player is prompted to roll the die
- Player presses the die icon, which causes the fair six-sided dice to roll and select a number
- User rolls a double and the user displays a prompt that the user should roll again
- Player position is updated in backend
- The player’s piece moves the chosen number amount of spaces and is visualized

2. Allow the user to purchase property by landing on non-property
- Player lands on an unowned property tile
- Player is prompted to buy or auction the property
- Player chooses the buy option, and if they have sufficient funds, they purchase the property and obtain the property card
- Property is added to the Player's propertyList instance variable

3. Allow the user to use the special effects
- User chooses a chance card from their pile of cards and consumes it
- The action is performed in backend, possibly changing Player data
- The result is displayed on GUI

4. Allow the user to go bankrupt and lose game
- Player’s is taxed money
- The player's money instance variable decreases
- A little icon displaying the decrementing money from the user
- The money UI shows an animation which decreases the money
- The player receives a prompt alerting them they have lost the game

5. Run a series of actions when landed on Start tile
- Player lands on GO tile
- The player's money instance variable increases
- A little icon displays showing the player’s money incrementing
- The money UI shows an animation which increasing the money

