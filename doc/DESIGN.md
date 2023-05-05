# OOGASalad Design Final
### TEAM NUMBER: 03

# Team Members and Role(s)
* Dominic Martinez (dcm67)
* Woonggyu Jin (wj61)
* Trevon Helm (tmh85)
* Jason Fitzpatrick (jf295)
* Jay Yoon (jy320)
* Chika Dueke-Eze (cgd19)
  * Worked on all things extensions-related, e.g Authentication/DB, `GameLauncher`, `SocialCenter`, `UserProfiles` etc
* Nathaniel Wullar (nkw14)
* Changmin Shin (cs521)
* Aloye Oshotse (ajo24)
  * GamePlay Frontend - Game Pieces, Dice, Cards, Card and Hand Popups.
* Matthew Traum (mnt20)


## Design Goals

The overall goal of a board game authoring platform is to provide a flexible, user-friendly
environment for game designers to create, customize and deploy their own board games. Such a
platform should include a range of tools and resources that allow designers to create custom boards,
game piecs, and rules, as well as the ability to publish, share and play their games with a wider
audience. The ultimate aim is to empower designers to create engaging and innovative board games
that provide a unique and enjoyable experience for players. Therefore, the main focuses of the
project is as follows:

- This project should be able to support a variety of different board games
- We use a flexible rule/action architecture to encapsulate game-specific assumptions
- Data is encapsulated within a `Metadata` class so that many types of objects can be edited via a
  generic option pane

## Design Primary Architecture

![Main](https://user-images.githubusercontent.com/90667275/229265134-a38ef80b-a7cd-43fd-8ceb-aa34899c8d34.jpg)

## Assumptions

For this project, we make the following assumptions:

- The game is a turn-based board game
- The board is composed of discrete tiles
- The game has viewPieces that are always on a tile

However, for the purposes of the design goals, we **do not** assume that:

- All actions involve the current player
- The game can only be played on a specific shape of the board
- The connections between tiles is fixed (rather, it may be cyclical, have multiple
  outgoing/incoming edges, no connections, etc.)
- The game objective is fixed
- The game viewPieces always move in a certain way (e.g. via a dice roll)

## Design Details: Classes and APIs

### Backend
#### Authentication
We designed our authentication process through interfaces; we have a simple `AuthenticationHandler` interface which any concrete auth impl has to implement. This allows for flexibility in what authentication solutions we use in the backend, while enabling our frontend (or whichever client uses the authentication modules) to not have to worry about any changes.
```java
public interface AuthenticationHandler {
  void login(String username, String password);
  void logout();
  void register(String username, String password);
  boolean getUserLogInStatus();
  String getActiveUserName();
  String getActiveUserID();
}
```
#### Database
Similar to authentication, we also designed our DB accessors through interfaces. We utilized the Data Access Object (DAO) design pattern, to provide a clean and modular way to hide the specific implementation details of the database and provide a consistent interface to the frontend consuming the APIs. We also provide `Schema` enums which essentially serve as a contract on what fields are currently accessible from the DB. This way we provide a centralized source of truth for the client to use when retrieving data from database reads.
```java
//GameDao.java
public interface GameDao {
  Map<String, Object> getGameData(String gameID);
  String createGame(String username);
  void updateGame(String gameID, Map<String, Object> game);
  List<String> getAllGames();
  void postGameReview(String review, String gameID, String userID);
  void deleteAllGames();
}

//UserDao.java
public interface UserDao {
  String getUserID(String userName);
  Map<String, Object> getUserData(String userID);
  boolean isUserRegistered(String username);
  String registerNewUser(String username, String password);
  void incrementNumberOfGamesPlayed(String userID);
  void updateUserName(String userID, String newUsername);
  void updateUserFullName(String userID, String newUserFullName);
  void subscribeToGame(String userID, String gameID);
  void unsubscribeToGame(String userID, String gameID);
  void deleteGame(String gameID);
  void updatePassword(String userID, String newPwd);
  void updateEmailAddress(String userID, String email);
  void updateUserPronouns(String userID, String pronouns);
  void updateAge(String userID, int age);
  void updatePreferredTheme(String userID, String preferredTheme);
  void updatedPreferredLanguage(String userID, String preferredLang);
  void deleteAllUsers();
}
```

#### Board

We represent the board as a directed graph. Rules can access the list of outgoing/incoming edges and
move the player appropriately.

#### Rule

The behavior of the game is defined by `Rule`s. A rule subscribes to events, such as the start of a
turn or landing on a tile. Rules do not change the game state on their own. Instead, rules
add `Action`s to the `ActionQueue`. The `ActionQueue` is a priority queue that processes actions,
such as move forward N spaces or set property X, in order. Actions change the current state, and
also trigger events, which in turn run the rules that subscribed to them, and so forth.

Rules encapsulate all game behavior. For instance, the behavior of a turn based game might be
represented by the following rules:

```java
class StartTurnRule {
  
  void registerEventHandlers(EventEmitter emitter) {
    emitter.onEvent(START_TURN, this::onTurnStart);
  }

  void onTurnStart(Event e, ActionQueue actionQueue) {
    // Has the lowest priority among actions in this game
    actionQueue.add(new EventAction(END_TURN), 10);
  }
}

class EndTurnRule {

  void registerEventHandlers(EventEmitter emitter) {
    emitter.onEvent(END_TURN, this::onTurnEnd);
  }

  void onTurnEnd(Event e, ActionQueue actionQueue) {
    // Actions with the same priority execute in the order they were added
    actionQueue.add(new SetMetadataAction(CURRENT_PLAYER, nextPlayer()), 1);
    actionQueue.add(new EventAction(START_TURN), 1);
  }
}
```

It is the game designer’s responsibility to ensure that actions execute in the correct order. For
instance, if a Monopoly doubles rule naively added an `EventAction(START_TURN)` with low priority, a
new turn would be processed before the player had a chance to finish their original turn. A correct
design would either add the action to the queue with lower priority, or use metadata to determine
when to add the action.

#### Choice

Whenever a decision happens, a choice with a certain strategy is triggered. For example, rolling a
die is a choice among the numbers 1-6 with a random strategy, and whether to buy a property is a
selectable choice presented to the player. The frontend can specialize certain types of
choices (like dice or choosing a tile) for better visuals.

#### Data classes (Tile, Property, etc.)

We strictly separate logic from data classes. Everything the game builder edits are contained in
data classes, which are then serialized to JSON. These should be able to be deserialized within the
game player and used appropriately.

#### Metadata

Most data is stored using `Metadata`. Metadata is a key-value store, where the values can be of
a variety of data types. Each data class (e.g. Tile, Player, Game) has dynamic metadata. This
metadata is segregated into:

- Always present: Keys that are always expected to have defined values to on a specific data class (
  e.g. a Tile must have a position)
- Fully editable: Keys that are specific to a certain game, and can be added/deleted within the
  builder.

This metadata is used for the frontend’s generic options pane, where any editable object can be
clicked and have their metadata modified. The majority of game data is kept this way.

### Frontend
#### Extensions
 The `TabExplorer` class is at the core entry point to the game player, game builder, and other extensions; it basically serves as a controller of sorts between navigating to the different parts of the application. The main APIs for this class, includes:
* `render()`: This is called at the start of the program launch, to set the stage and scene.
* `displayDefaultTab()`: This method basically checks whether a user is logs in or not. If the former, then it allows a user to access the various parts of the application (`GameLauncher`, `SocialCenter`, `Settings`); but, if the user isn't logged in, it prompts a user to then login.
* `setCurrentTab()`: This method is called by the various `Tab`s `renderTabContent()` method to display a `Tab`s node on selection.

These are the core APIs that power the `TabExplorer`, but each individual `Tab` has flexibility in how they choose to implement their display or navigation within their own tab. For example, the `SettingsTab` which is a high-level `Tab` (i.e. can be accessed from the `NavBar`) has multiple 'sub-tabs' within it. If you go to the `SettingsTab` you'll see that you can choose to go to any of the various settings option (Accounts, Region/Language, Appearance), however, the `SettingsTab` chooses how it wants that 'internal' navigation to work and not the `TabExplorer`.

In essence, the `TabExplorer` is responsible for navigating to `Tab`s accessible from the `NavBar` and utilizes simple publics APIs and design patterns like `Factory` to do this.

#### Builder
From an API perspective, the Builder’s capabilities are limited– you can only load in a game file
and save the created game to a file. Since our builder creates the game file, it does not need to
communicate much with the rest of the project, so its API is more limited.

Design wise, the builder interfaces primarily with the Tile abstraction, which will represent each
piece on a game board. This abstraction will hold the actions for a tile, its appearance, and where
it will go next. The builder’s main objective will be connecting these tiles together to create a
working game.

The builder will also allow adjustments of the entire game's rules, which can be done with a Rules
abstraction. This will dictate what rules certain tile types follow, such as Chance tiles allowing
the player to draw a Chance card. These rules should also allow the game to be open to further
extensions along the way, such as new kinds of tiles and new ways to play the game.

These two abstractions will make it easier to develop a method for the player to interact with the
games, and it should also provide a clearer path to what these abstractions represent in the backend
as well.


- `GameView`: Defines the structure and specifications of the window of the game
- `GameBoard`: Defines the methods and properties required for a game board, such as its size, layout, and any components.
- `UserInterface`: Defines the methods and properties required for a user interface, such as menus, buttons, and other graphical elements.
- `InputListener`: Defines the methods and properties required for an input listener, which listens for user input and triggers appropriate actions.
- `GamePiece`: Defines the methods that set the properties of each game piece and controls the use of it
- `Player`: Defines the methods and properties required for a player in the game, such as their name, score, and other relevant data.

- `GameView`: Defines the structure and specifications of the window of the game
- `GameBoard`: Defines the methods and properties required for a game board, such as its size, layout, and any components.
- `UserInterface`: Defines the methods and properties required for a user interface, such as menus, buttons, and other graphical elements.
- `InputListener`: Defines the methods and properties required for an input listener, which listens for user input and triggers appropriate actions.
- `GamePiece`: Defines the methods that set the properties of each game piece and controls the use of it
- `Player`: Defines the methods and properties required for a player in the game, such as their name, score, and other relevant data.

## Assumptions or Simplifications
* For authentication, one simplification made was user sign-ups occur when a user tries to login with a username that doesn't exist. For example, if you try logging in with `user123`(username) and `pwd123`(password), our system first checks if `user123` already exists; if it does, then it validates the password, but if it doesn't it creates a new account with the credentials inputted by user. 


## Changes from the Plan
* One major change that the database accessor API had was delivering all the data to a user as a `Map<String, Object>` object, vs exposing individual method calls to access different fields in the DB. For example, the API originally included methods such as:
  ```java
    // for user
    public String getUserPronouns(String userID);
    public String getUserEmail(String userID);
    public Double getUserAge(String userID);
    ...
    
    // for game
    public String getGameTitle(String gameID);
    public String getGameDescription(String gameID);
    ...
    ```
  But we then changed it to simply have one method to retrieve all the values in a document and then make the user get the specific value they wanted
  ```java
  // for user
  Map<String, Object> getUserData(String userID);
  //for game
  Map<String, Object> getGameData(String gameID);
  ```
    We noticed that the former approach had a couple of problems in our context:
    1.  It was not efficient: The way our DB is structured is such that getting the value of just a single field vs getting the value of all the fields are about the same latency. This means that for scenes where you need to get different values from the database, a user would have to make API calls multiple times to the DB to access info, when they could simply make one call and get all the values they need.
    2. This choice also made our interface bloated, because it resulted in having interfaces with a lot of methods that were really doing the same thing for the most part, just with different parameters.

* **TODO @everyone, add more changes you made if any**



## How to Add New Features
* If you wanted to add a new tab, let's call it `NewTab` to the `TabExplorer` to display info on something, you would create a new `NewTab` class that implements `Tab` and then implement the `renderTabContent()` for the node you want displayed. Depending on specifics, you'll probably also have to add it to the `NavBar` and add the `setOnAction()` method. Also, you would have to add this to the `TabFactory` so that the `TabExplorer` can create an instance of it.
* To add a new field to the database, you simply have to create a new entry in either the `UserSchema` or `GameSchema`. If this field is something that you want the client to be able to update, you also need to include an update method for it in the `GameDao`/`UserDao`.
