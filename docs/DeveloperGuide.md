# Developer Guide

## Table of Contents

* [Design](#design)
  * [Architecture](#architecture)
    * [Main components](#main-components)
    * [Component interaction](#component-interaction)
  * [UI components](#ui-components)
  * [EconoCraftLogic components](#econocraftlogic-components)
  * [MiniGame components](#miniGame-components)
* [Implementation](#implementation)
  * [MiniGame - Typing Game](#miniGame---typing-Game)
  * [MiniGame - Tic Tac Toe](#miniGame---tic-tac-toe)
  * [MiniGame - True or False](#miniGame---true-or-false)
* [Product scope](#product-scope)
  * [Target user playerProfile](#target-user-playerprofile)
  * [Value proposition](#value-proposition)
* [User Stories](#user-stories)
* [Non-Functional Requirements](#non-functional-requirements)
* [Glossary](#glossary)

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design

### Overview of GameFlow
![GameFlow.jpg](UML%20diagram%2FGameFlow.jpg)

### Architecture

![Architecture.png](UML%20diagram%2FArchitecture.png)

The **Architecture diagram** above showcases the high-level design of the EconoCraft Pro application. 

The following section would give a brief overview of the different components of the application and their interactions
with each other.

#### Main components:

`Main` (consisting of class `EconoCraft` and `EconoCraftLogic`) 
is in charge of the game initialization and starting the main game loop.

* At game initialization, it reads the necessary information from prompted user input and develops the player profile.
* When game starts, it initializes the game logic and starts the main game loop.

The functionality of the game is divided into different components, each responsible for a different aspect of the game.

* `PlayerProfile`: Represents the player's profile and is responsible for storing and updating the player's information.
* `Parser`: Responsible for parsing the user input.
* `ResponseManager`: Responsible for generating the response to the user input.
* `EconoCraftLogic`: Responsible for executing user command and updating the game state.
* `MiniGame`: Responsible for handling the mini-games that the player can play to gain rewards.
* `CommandFactory`: Responsible for using the parsed user input to produce executable commands.

#### Component interaction:

The *Sequence Diagram* below showcases the interaction between the different components of the EconoCraft Pro 
application when a user inputs a command `work`.

For `CommandFactory` and `Minigame`,
* Each defines its API for creating commands and mini-games respectively, where
  `Command` is the API for `CommandFactory` and `MiniGame` is the API for `MiniGame`.
* Implements its functionality with concrete classes such as `WorkCommand` and `TypingGame`.

The sections below would give more details of each component.

## UI components

UI consists of the following components:
* `Parser`
* `ResponseManager`
* `CommandType`

![UI.png](UML%20diagram%2FUI.png)

The **UI components**,
* `Parser` parses the user input for `CommandFactory` to produce `Command`
* `ResponseManager` generate the response to the user according to the *command execution* and *game logic*.

## EconoCraftLogic components

Here is the partial class diagram of the `EconoCraftLogic` component:

![Logic.png](UML%20diagram%2FLogic.png)

> [!NOTE]
> - The `XYZ` in `XYZCommand` represents the exact command e.g., `WorkCommand`, `RestCommand`, `ExerciseCommand`.
> - The `XYZ` in `XYZMiniGame` represents the exact mini-game e.g., `TypingGame`, `TicTacToe`, etc.
> - The `XYZ` in `StockXYZ` represents the stock label e.g., `StockOne`, `StockTwo`, etc.

Here is the sequence diagram of the `EconoCraftLogic` executing the command:

![Work.png](UML%20diagram%2FWork.png)

The `EconoCraftLogic` mechanism:

1. `EconoCraftLogic` receives the user input string and pass it into `CommandFactory`.
2. `CommandFactory` would use `Parser` to parse the user input string and produce a `Command`.
3. `EconoCraftLogic` would execute the `Command` which will:
   * update the player profile accordingly.
   * use `ResponseManager` to generate the response to the user.

Here is the sequence diagram of `stock` command:

![Stock.jpeg](UML%20diagram%2FStock.jpeg)

The mechanism:
1. `stock` command invoke the `StockCommand` class.
2. `StockCommand` invokes the `start` method for `StockActivate` class.
3. `StockActivate` class calls a new `StockStorage` class.
4. `StockStorage` class will new execute its play function and enable user to buy stocks.

Here is the sequence diagram of `sellstock` command:

![SellStock.jpeg](UML%20diagram%2FSellStock.jpeg)

The mechanism:
1. `sellstock` command invoke the `SellStockCommand` class.
2. `SellStockCommand` invokes the `getAsset` method for `PlayerProfile` and fetch the `Asset` class.
3. `Asset` class executes the `sellStock()` function and sell all of the stocks the player currently possess.
4. Information related to stocks will be updated inside `Asset` and `PlayerProfile` class.

![Save.png](UML%20diagram%2FSave.png)

The mechanism:
1. `Saver` will be called within `startEcono` method after one command is executed.
2. `Saver` creates a new `file` class to write the `PlayerProfile` class into Json file.
3. `Saver` class calls `constructionJson` method from `Serializer` class to handle complex data structures convert them
into Json format.
4. `Saver` class calls `write` and `flush` method from the `file` class to write to the json file.

![Load.png](UML%20diagram%2FLoad.png)

The mechanism:
1. `Loader` will be called within `initializeGame` method once the program is initialized.
2. `Loader` invokes the `readJsonFromFile` method to read data from the Json file and creates a new `file` class.
3. `Loader` invokes the `decodePlayerProfile` from `Decoder` method to handle Json file and convert it into complex 
data structures.
4. `Loader` returns the `PlayerProfile` class to load the previous information.


## MiniGame components

Here is the partial class diagram of the `MiniGame` component:

![MiniGame.png](UML%20diagram%2FMiniGame.png)

The `MiniGame` mechanism:
1. For command `work`, `rest`, and `exercise`, they would have their respective mini-games.
2. When these commands are generated and executed in `EconoCraftLogic`, the respective mini-game would be played.
3. The command would then update the player profile according to the mini-game result.

# Implementation

## MiniGame - Typing Game

The implementation of the Typing Game is as follows:

1. The game can be invoked by the `WorkCommand` class when the user inputs the `work` command.
2. It makes use of the `ResponseManager` to generate the instructions of the game to the user. 
3. The user would be prompted to type the given text as fast as they can.
4. This game made use of the `CompletableFuture` class to create separate thread which handles the countdown timer and user input at the same time.
5. When the user finishes typing or the time limit is reached, the game would calculate the user's typing speed and accuracy and reward the user accordingly. 
6. Finally, the `WorkCommand` would update the player profile with the reward earned according to the user's performance in the game.

## MiniGame - Tic Tac Toe

The implementation of the Tic Tac Toe Game is as follows:

1. The game can be invoked by the `ExerciseCommand` class when the user inputs the `exercise` command and select option `1`.
2. It makes use of the `ResponseManager` for instructions and display the board status.
3. The user would be prompted to choose player mark and place it when entering the game.
4. AI player will randomly choose available to place mark once user places the mark.
5. After each placement, the game would check the status to determine whether continue the game or not.
6. If the board is full or there are three consecutive marks, the game will announce the winner or say "it's a draw".
7. Finally, the `ExerciseCommand` would update the player profile with the reward or punishment for health.

## MiniGame - Hangman

The implementation of Hangman is as follows:

1. The game can be invoked by the `ExerciseCommand` class when the user inputs the `exercise` command select option `2`.
2. It makes use of the `ResponseManager` for instructions and display the word with one missing character.
3. The user would be prompted to guess the missing character.
4. After `5` rounds, the game will show congratulation message if total wrong guesses are fewer than 3 and failure message vice versa.  
5. Finally, the `ExerciseCommand` would update the player profile with the reward or punishment for health.

## MiniGame - True or False

1. The game can be invoked by the `RestCommand` class when the user inputs the `rest` command.
2. It makes use of the `ResponseManager` for instructions and display the questions to answer.
3. The user would be prompted to answer True or False by typing `T` or `F`.
4. When the user returns his/her answer, the `ResponseManager` will display the correctness of the response.
5. When the user finishes answering all the questions, `ResponseManager` will output how many questions the user answers correctly.
6. Finally, the `RestCommand` would update the player profile with the reward or punishment earned according to the game.

## Investment game - Stock

1. The process of buying stock can be invoked by the `StockCommand` class when user inputs `stock`.
2. It makes use of `ResponseManager` for instructions and output the stock graph and information relating to the stock.
3. The user would be prompted to input the number of stocks they would like to purchase in integer.
4. `ResponseManager` will indicate the completion of purchase after user's input.
5. When the user finishes the purchase, stock they bought will be stored inside `Asset` class.
6. Input `status` will output the stock the user currently possesses.
7. The process of selling stock can be invoked by the `SellStockCommand` class when user input `sellstock`.
8. The system will calculate user's profit gained at the current price of the stock and return the money back to the 
user based on the stock's current price.
9. User can keep the stock they purchased inside their asset for as long as they want.

## Investment game - Bond

1. The process to purchase bonds can be invoked by the 'BondCommand' when the user enters 'bond'.
2. The system uses 'ResponseManager' to display a list of available bonds and guide the user through the selection process.
3. Users are prompted to select which bond they wish to buy by entering a number corresponding to the bond displayed on the screen.
4. After selecting a bond, users are asked to specify the number of units they want to purchase by entering an integer.
5. The transaction is confirmed by 'ResponseManager', which provides details of the total investment and expected returns based on the bond's performance.
6. The purchased bonds are then recorded in the 'Asset' class under the user’s profile, updating their financial portfolio.
7. Users can type 'status' to view details about the bonds they own, including the number of units and the accumulated interest.
8. The user's total assets are updated to reflect their current bond holdings and the impact of these investments on their overall financial status.

## Investment game - Crypto

1. The process to purchase cryptos can be invoked by the 'CryptoCommand' when the user enters 'Crypto'.
2. The system uses 'ResponseManager' to display a list of available cryptos and guide the user through the selection process.
3. Users are prompted to select which crypto they wish to buy by entering a number corresponding to the crypto displayed on the screen.
4. They are then prompted to enter the amount of USD they wish to invest in their selected cryptocurrency.
5. After the investment amount is entered, the system calculates potential returns based on current market trends and adjusts the user's assets accordingly.
6. A confirmation message is displayed to the user detailing the investment amount and the expected changes due to market conditions.
7. The cryptocurrencies bought are added to the user’s 'Asset' class, displaying both the amount invested and the type of cryptocurrency.
8. By typing 'status', users can check their cryptocurrency holdings and the current market value of these investments.
9. The game dynamically simulates market changes, which can affect the value of the cryptocurrencies held by the user, and these changes are reflected in the updated asset information. Reflected in the user's asset status.

## Company Management
1. Players are able to invoke different commands to manage their company.
2. The corresponding commands will be produced from parsing the user input by using `CommandFactory`.
3. Then the commands produced will be access and executed by `Command` interface in `EconoCraftLogic`.
4. Different command would adjust the Company's status such as hire employee, fire employee, and raise salary etc.
5. At the end of the game round, the company would earn asset to the player based on the company's status.

## Random Event
1. Random event will be triggered at the end of each round.
2. The event will be generated by `EventGenerator` class.
3. The event generated will be executed via `RandomEvent` interface in `EconoCraftLogic`.
4. Player would be prompted to make a decision based on the event.
5. Based on the decision made and the event type, the player would receive different rewards or punishments.
6. The event would add uncertainty and excitement to the game.

## Product scope
### Target user playerProfile

{Describe the target user playerProfile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ...         | I want to ...                                                  | So that I can ...                                                       |
|---------|------------------|----------------------------------------------------------------|-------------------------------------------------------------------------|
| v1.0    | new user         | see usage instructions                                         | refer to them when I forget how to use the application                  |
| v1.0    | user             | view basic command list and game flow                          | navigate and start the game smoothly                                    |
| v1.0    | user             | add my name to the programme                                   | have a personalised game interaction                                    |
| v1.0    | user             | exit from the programme                                        | decide when to exit based on my own preferrence                         |
| v1.0    | user             | view my player or company status                               | know how much assets that I have                                        |
| v2.0    | user             | find a to-do item by name                                      | locate a to-do without having to go through the entire list             |
| v2.0    | user             | restart the game                                               | reset the progess when a bad decision is made                           |
| v2.0    | user             | check current game progress via a progress percentage bar      | be clear about how many more task needs to be done to complete the game |
| v2.0    | user             | set finantial task to be completed                             | complete task to increase game progress                                 |
| v2.0    | user             | track my in-game money flow                                    | play the game more strategically                                        |
| v2.0    | first-time user  | view tips for playing the game                                 | familiarise with the game faster                                        |
| v2.0    | first-time user  | view the background story of the game                          | have a better understanding on what the game is about                   |
| v2.0    | experienced user | perform employee hiring action                                 | develop the virtual startup                                             |
| v2.0    | user             | invest in virtual stock market                                 | increase the earning for company development                            |
| v2.0    | user             | delete a task that has been created                            | start over with a new idea or approach                                  |
| v2.0    | user             | have a reward system for completing each task                  | feel a sense of achievement when complete the task                      |
| v2.0    | player           | task with different challenge when entered command             | feel more engaged to the game                                           |
| v2.0    | user             | want to have random event at the end of each round             | enjoy the uncertainty within the gameplay process                       |
| v2.0    | user             | save my personal profile                                       | not to lose any progress when I play again                              |
| v2.0    | user             | see different game ending from different decision made in game | gain a sense of satisfaction when good ending happens                   |
| v2.0    | user             | have 3-4 saved progresses                                      | choose the progress based on my preference                              |
| v2.0    | user             | name each progress                                             | differentiate saved progresses                                          |
| v2.0    | user             | delete saved progresses                                        | delete the saved data when the data has no use                          |
| v2.0    | advanced player  | modify the saved file                                          | adjust my game information when possible                                |
| v2.0    | user             | choose level of difficulties of the game                       | adjust the difficulties based on my skills                              |
| v2.0    | user             | learn different player skills                                  | complete mission and task easier                                        |
| v2.0    | user             | have lucky draw to gain interesting rewards                    | feel the excitement and joy of the game                                 |
| v2.0    | user             | get loan from the virtual bank                                 | use for the investment and expand the businesses                        |
| v2.0    | user             | negotiate with game NPCs                                       | feel more engaged to the game                                           |
| v2.0    | busy-user        | save the game data into local                                  | continue the saved progress next time                                   |
| v2.0    | first-time user  | see what actions I can do in different player level            | type correct command to complete the tasks                              |
| v2.0    | user             | trigger random event                                           | feel more insterested to the game                                       |




## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
