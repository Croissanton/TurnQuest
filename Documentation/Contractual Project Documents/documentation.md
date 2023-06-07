# TurnQuest Documentation


## The aim of the project

The aim of the project is creating a desktop, 2D, multiplayer game that will be engaging while not being addictive. 


## Overview of the game

The game is based on completing consecutive stages by fighting the monsters associated with the given stage. The player has its own inventory to store his items. Items can be purchased in the implemented shop. Multiple players can't play the game at the same time, however can have distinguish accounts on the same machine with their progress saved. The game is meant to be entertaining but not addictive, therefore special "energy system" is introduced. Player can play the game until he has energy left.  After running out of the energy he needs to wait some time for the energy to be renewed.


## Game Design

The game is run by the library **libgdx**. **Gradel** is the tool used to build the application.

The design of the game consist of fifth modules.

- Core
- Desktop
- Assets
- Data
- Tests


## Core

This module contains barely all the source code of the game. Consist of eight sub-modules and the main game class.

- Animations
- Assets
- Dialogs
- Entities
- Logic
- Network
- Screens
- Tests
- Utils
- TurnQuest - the main class

### Animations

This module is responsible for managing animations in the game. It consists of a one class which main function is storing animations in the java Map.

#### Classes

- AnimationHandler
	-Responsible for handling more animations, it should be used when more than one animation is required in the screen.

### Assets

This module is made of one class  - Assets, that is a singleton class that provides access to the AssetManager and contains constants for all assets used in the game. One of the main function of this class is choosing appropriate background texture for the used screen class.

##### Classes

- Assets

### Dialogs

This module containing all the dialog classes in the game. Dialogs are the popping  windows used to confirm some operations. There are used in the all game navigation screens for acknowledging various operations.

#### Classes

- AbilitiesDialog
	- Shows on the screen abilities of the players character in the CombatScreen.
	- Based on the payer's character class chooses appropriate abilities. 
	- Includes some combat logic.
- ConfirmationDialog
	- Used to confirm changing the screen to another.
- CreateClanDialog
	- Responsible for creating a new clan with help of ClanManager class.
	- Checks all the necessary condition for creating a clan.
- DeleteClanDialog
	- Responsible for deleting a clan with help of the ClanManager class.
- JoinClanDialog
	- Responsible for joining a clan with help of the ClanManager class.
- LeaveClanDialog
	- Responsible for joining a clan with help of the ClanManager class.
	- Checks all the necessary conditions that need to be fulfilled to join the chosen clan.
- LevelUpDialog
	- Show on the screen information about achieving a new level.
- GameOverDialog
	- Shows on the screen the information about a lost fight.
- VictoryDialog
	- Shows on the screen the information about a won fight.
- GameSelectionDialog
	- Used in the MenuScreen to choose either the player wants to create a new account or sign up to continue the saved game state on his account.
- InformationDialog
	- Shows on the screen information associated with the just done action of the player.
- LoginDialog
	- Used while logging to the game.
	- Includes some logic connected to logging into the game.
- PreferencesDialog
	- Used to adjust volume and full-screen mode settings.
- SingUpDialog
	- Used while signing up to the game.
	- Here the player can choose which character he/she wants to be in the game.
	- Includes some logic connected to signing up to the game.
- TutorialDialog
	- Displays a dialog with a tutorial depending on the string provided in the parameter. 
	- Current tutorials: mainmenu, combat, boss, abilities, inventory, clan, shop, player, game, clan


### Entities

This module contains entities used in the game - player, enemies and items. Classes that contains logic of the given entity and the state in which it is in a given moment.


##### Classes

- Character
	- The base class for the Player and Enemy classes.
	- Consist of stats of the entity and appropriate getters and setters.
- Player
	- Inherits from the Character class.
	- Object of this class is created always after logging in or signing up.
	- It contains the player stats and the necessary logic.
	- On the base of chosen character class sets up the player's inventory and stats. 
- Clan
	- Used to group players - team up players.
	- Contains a list of players names.
- Enemy
	- Inherits form the Character.
	- Represents enemies met by the player in the stages.
	- It contains enemy stats.

### Logic

This module contains classes responsible for the game more advanced logic such as combat system.

#### Classes


- CombatLogic
	- Class responsible for combat system in the game.
	- Contains methods responsible for players and enemies attacks.
	- Allows to choose appropriate ability for the particular player's character class.
	- Manage choosing different types of attacks.


### Screens

This module containing all the classes linked with displaying the game on the screen. It's strictly linked with the libgdx framework. 


#### Classes

- BaseScreen
	- An abstract class from which inherits all the other screens.
	- Used to partially implement methods provided by the Screen interface form the libgdx.
- MainMenuScreen
	- Displays the screen which is the first screen to see in the game.
	- Contains options of quitting the game or logging in.trail
- GameScreen
	- The main screen with game options to choose.
	- From this screen it's possible to navigate to the MapScreen, InventoryScreen, ShopScreen or opening PreferencesDialog.
- ShopScreen
	- Shows items available to buy or sell in the shop.
	- Implements functionality of buying or selling items.
- InventoryScreen
	- Shows items that are in the player's inventory.
- MapScreen
	- Used to select appropriate level or mode of the game.
- CombatScreen
	- Displays combat stage with players, allies and enemies.
	- Handles animations by the class AnimationHandler.
	- Handles combat logic by the class CombatLogic.
- ClanScreen
	- Shows the information associated with clans in the game and particularly player's clan.
- BossScreen
	- Similarly to the CombatScreen shows the combat stage, however it shows the boss fight stage - which is meant to be harder to the player than the normal combat.
	- Handles animations by class AnimationHandler.
	- Handles combat logic by class CombatLogic.
- AbilitiesScreen
	- Shows on the screen abilities available in the game.
	- Manages logic of the abilities.
	- Marks which abilities are already available for a player.
	- Manages unlocking abilities by a player.

### Utils

This module is responsible for managing data stored in the game.

#### Classes

- AnimationHandler
	- A class responsible for managing and storing animations in the game.
- ClanManager
	- A class responsible for managing clans data in the game.
	- Reads the clan's data that are stored in a json file.
- EnemyManager
	 - A class responsible for managing enemies' data in the game.
	- Reads the enemies' data that are stored in a json file.
- PlayerManager
	 - A class responsible for managing player's data in the game.
	- Reads the player's data that are stored in a json file.
- UserManager
	- A class responsible for managing authentication data in the game.
	- Through this class user is added to the database, user's authentication is checked.
	- Class contains also function used for 
	- hashing the user's password.


### TurnQuest class

- Inherits from the libgdx's Game class.
- Contains default settings of the game behaviour as well as getters and setter of the game properties.


## Desktop

This module contains DesktopLauncher class which is responsible for launching the whole game, it creates TurnQuest object. It consist also of build.gradle file which is an instruction of how the project should be build.


## Assets

This module contains assets needed by the game such as fonts, images and music.


## Data

This module contains json files in which state of the game is saved.


## Tests

This module contains tests made for the game logic. The tests mainly focus on the combat logic and validation of player's credentials. The framework used for testing is **junit** and **mockito**.
- CombatLogicTest
	- Tests the CombatLogic class by checking the limits of the combat such as having no HP, no STR or no MP.
- UsersTest
	- Tests that the users are correctly stored into the json.
	- Also tests what happens when trying to create an existing user.
