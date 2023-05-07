# TurnQuest Documentation

## Aim of the project

The aim of the project is creating a desktop, 2D, multiplayer game that will be engaging while not being addictive. 

## Overview of the game

Game is based on completing consecutive stages by fighting the monsters included by the stage. The player has its own inventory to store his item. Items can be purchased in the implemented shop. Multiple player can't play the game at the same time and interact with each other but can have distinguish account on the same machine with their progress saved. The game is meant to be entertaining but not addictive, therefore special "energy system" is introduced. Player can play the game until he has energy left.  After running out of the energy he needs to wait some time for the energy to be renewed.

## Game Design

The game is run by the library **libgdx**. **Gradel** is the tool used to build the application.

The design of the game consist of eight modules and the main class.

- assets
- dialogs
- entities
- logic
- network
- screens
- tests
- utils
- TurnQuest - the main class


### Assets

To be finished

### Dialogs

Module containing all the dialog classes in the game. Dialogs are the popping  windows used to confirm some operation. There are used in the all game navigation screens for acknowledging various operations.

#### Classes

- AbilitiesDialog
	- Shows on the screen abilities of the players character in the CombatScreen.
	- Includes some combat logic.
- ConfirmationDialog
	- Used to confirm changing the screen to another.
- GameSelectionDialog
	- Used in the MenuScreen to choose either the player wants to create a new character or sign up to continue the saved game.
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


### Entities

Module containing entities used in the game - Player, player's allies, enemies and items. Classes contains logic of the given entity and the state in which it is in a given moment.


#### Classes

- Character
	- The base class for the Player, Enemy and Ally classes.
	- Consist of stats of the entity and appropriate getters and setters.
- Player
	- Inherits from the Character.
	- Object of this class is created always after logging or signing up.
	- It contains the player stats and the necessary logic.
- Ally
- Enemy
	- Inherits form the Character.
	- Represents enemies met by the player in the stages.
	- It contains enemy stats.
- Item
	- The class representing a specific item. Items can be stored either in the shop or in the player's inventory.
	- It contains item stats.

