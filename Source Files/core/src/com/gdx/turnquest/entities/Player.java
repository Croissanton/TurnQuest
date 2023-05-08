package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

/**
 The Player class represents a playable character in the game. It extends the Character class and has additional fields
 for character class, gold, experience points, level, and inventory. It also provides methods for managing the player's
 inventory and game statistics, such as adding or removing items, setting or increasing gold, experience points, and level.
 */


public class Player extends Character {

    private String characterClass;
    private int gold;
    private int exp;
    private int level;
    private ObjectMap<String, Integer> inventory;
    private final String playerName;

    /**
     * Creates a default Player object with no parameters. The player's name is set to null.
     */

    public Player() {
        super();
        this.playerName = null;
    }

    /**
     * Creates a new Player object with a given player name. This constructor sets default values for the player's
     * character class, gold, experience points, level, and inventory.
     *
     * @param playerName The name of the player.
     */
    public Player(String playerName) {
        super();
        this.playerName = playerName;
        this.characterClass = "";
        gold = 0;
        exp = 0;
        level = 1;
        inventory = new ObjectMap<>();
        //TODO: add character class handling -> setting stats according to the class given.
    }

    /**
     * Sets the player's inventory to a given ObjectMap of items and their quantities.
     *
     * @param inv The ObjectMap containing the items and their quantities.
     */
    public void setInventory(ObjectMap<String, Integer> inv) {
        inventory = inv;
    }

    /**
     * Adds a given quantity of an item to the player's inventory. If the item is already in the inventory, its quantity
     * is increased by the given amount. If the item is not in the inventory, it is added with the given quantity.
     *
     * @param item The name of the item to add to the inventory.
     * @param quantity The quantity of the item to add.
     */
    public void addItem(String item, int quantity) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + quantity);
        } else {
            inventory.put(item, quantity);
        }
    }

    /**
     * Removes a given quantity of an item from the player's inventory. If the item's quantity becomes 0, it is removed
     * from the inventory entirely. If the item is not in the inventory or the quantity to remove is greater than the
     * quantity in the inventory, the method returns -1 to indicate failure.
     *
     * @param item The name of the item to remove from the inventory.
     * @param quantity The quantity of the item to remove.
     * @return 0 if the removal was successful, -1 if the removal failed.
     */
    public int removeItem(String item, int quantity) {
        if (inventory.containsKey(item)) {
            if (inventory.get(item) < quantity) {
                inventory.remove(item);
            } else {
                inventory.put(item, inventory.get(item) - quantity);
            }
            return 0;
        }
        return -1;
    }

    /**
     * Returns the ObjectMap containing the player's inventory items and their quantities.
     *
     * @return The ObjectMap containing the player's inventory.
     */
    public ObjectMap<String, Integer> getInventory() {
        return inventory;
    }

    /**
     * Returns the player's name.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player's character class to a given String.
     *
     * @param cc The name of the character class.
     */
    public void setCharacterClass(String cc) {
        characterClass = cc;
    }

    /**
     * Returns the player's character class.
     *
     * @return The player's character class.
     */
    public String getCharacterClass() {
        return characterClass;
    }

    /**
     * Sets the player's gold to a given amount.
     *
     * @param g The amount of gold to set.
     */
    public void setGold(int g) {
        gold = g;
    }

    /**
     * Adds a given amount of gold to the player's current gold.
     *
     * @param g The amount of gold to add.
     */
    public void addGold(int g) {
        gold += g;
    }

    /**
     * Removes a given amount of gold from the player's current gold. If the player does not have enough gold, the method
     * returns -1 to indicate failure.
     *
     * @param g The amount of gold to remove.
     * @return 0 if the removal was successful, -1 if the removal failed.
     */
    public int removeGold(int g) {
        if (gold < g) {
            return -1;
        }
        gold -= g;
        return 0;
    }

    /**
     * Returns the player's current gold.
     *
     * @return The player's current gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the player's experience points to a given amount.
     *
     * @param e The amount of experience points to set.
     */
    public void setExp(int e) {
        exp = e;
    }

    /**
     * Adds a given amount of experience points to the player's current experience points.
     *
     * @param e The amount of experience points to add.
     */
    public void addExp(int e) {
        exp += e;
    }

    /**
     * Gets the player's current experience points.
     * @return The player's current experience points.
     */
    public int getExp() {
        return exp;
    }

    /**
     * Sets the player's level to a given amount.
     * @param l The level to set.
     */
    public void setLevel(int l) {level = l;}

    /**
     * Increases the player's level by a given amount.
     * @param l The amount to increase the level by.
     */
    public void increaseLevel(int l) {level += l;}

    /**
     * Gets the player's current level.
     * @return The player's current level.
     */
    public int getLevel() {return level;}
}
