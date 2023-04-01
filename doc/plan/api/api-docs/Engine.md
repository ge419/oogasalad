###Overview
The `Engine` class represents a concrete class that is responsible for implementing the logic of the game. This  API's design goals are to provide a flexible and expressive experience for other developers to easily create new features without worrying about whether it’ll run smoothly. It upholds the SOLID principles and Pillars of Object-Oriented Design by creating well-defined interfaces and abstract classes, using dependency injection to reduce coupling, and favoring composition over inheritance to promote extensibility. By adhering to these principles, the API provides a clear and consistent interface that allows for easy extension and modification, enabling developers to create new features quickly and efficiently. Additionally, the API utilizes standardized design patterns and conventions to promote consistency and ease of use, further encouraging developers to work with it and create new features.

The Engine API can provide for extension by allowing developers to add new rules to the game without modifying the core game engine. This can be achieved by using interfaces such as the `Rule` interface, which can be implemented by custom rule classes. This approach adheres to the Open/Closed Principle of SOLID design, which states that classes should be open for extension but closed for modification, because the `Engine` takes in a list of `Rule`s and so adding more concrete classes that implement the `Rule` interface would be fine.

### Details
The `Engine` class serves as the core logic for the game, by  implementing the game rules and mechanics. On an event trigger, this API functions by going through a set list of 	`Rule`s, adding `Action`s to a queue (if needed), executing these actions (which in turn trigger events), and then cycles back to checking the list of `Rule`s. This is a core API responsible for the continuous game loop.
The idea is that the controller would use the `Engine`’s `run()` method and also pass in a list of rules. Then the `Engine` would basically go through the rules and add actions as needed on event triggers.

### Classes/Examples
Located in `./Engine.java` file.

### Considerations/Design Discussions
Some main assumptions we have for this API, includes that all tiles will have distinct types associated with them that specify what rules/actions should be implemented based on certain events.

A design discussion that we had was whether we wanted the rules to handle a new turn, i.e. if after executing a particular action for a rule, we wanted that rule to be able to push an action to the queue that’ll indicate that the current player’s turn is over and a new player’s turn is next. We felt that doing it this way might make the `Engine` have less code, however, it could potentially lead to more complexity on particular `Rule` classes. Right now, we’re leaning on leaving this responsibility to the `Engine`, but as we start working more on the code, moving this responsibility to `Rule` classes is something we might want to still consider.
