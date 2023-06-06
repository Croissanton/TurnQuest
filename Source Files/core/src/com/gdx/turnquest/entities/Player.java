package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;
import java.util.Arrays;

/**
 * The Player class represents a playable character in the game. It extends the Character class and has additional fields
 * for character class, gold, experience points, level, and inventory. It also provides methods for managing the player's
 * inventory and game statistics, such as adding or removing items, setting or increasing gold, experience points, and level.
 *
 * @author Cristian
 */


public class Player extends Character {

    private String characterClass;

    private int gold;

    private int exp;

    private int level;

    private ObjectMap<String, Integer> inventory;

    private final String playerName;

    private String clanName = "";

    private String[] equipment = new String[3]; //There should also be a slot for rings.

    private int[] equipmentStats = new int[7]; //The stats that the equipment gives.

    private int[] abilities = new int[4];

    private int energy;

    private int loginCount;

    private long previousTime;

    private int abilityPoints;

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
    public Player(String playerName, String characterClass) {
        super();
        this.playerName = playerName;
        this.characterClass = characterClass;
        gold =300;
        energy = 5;
        previousTime = System.currentTimeMillis();
        loginCount=1;
        gold = 0;
        exp = 0;
        level = 1;
        abilityPoints = 0;
        Arrays.fill(abilities, 0);
        inventory = new ObjectMap<>();
        inventory.put("Potion", 5);
        inventory.put("Ether", 5);
        calculateStats();

        switch (characterClass){
            case "Warrior" :
                inventory.put("Iron Sword", 1);
                setEquipment("Iron Sword");
                inventory.put("Iron Shield", 1);
                setEquipment("Iron Shield");
                inventory.put("Leather Armor", 1);
                setEquipment("Leather Armor");
                break;
            case "Archer" :
                inventory.put("Bow", 1);
                setEquipment("Bow");
                inventory.put("Old Quiver", 1);
                setEquipment("Old Quiver"); //Singular because it's a stack of arrows (infinite). There could be gold arrows, flame arrows, ice arrows, etc...
                inventory.put("Leather Armor", 1);
                setEquipment("Leather Armor");

                break;
            case "Mage" :
                inventory.put("Wooden Staff", 1);
                setEquipment("Wooden Staff");
                inventory.put("Old Grimmorie", 1);
                setEquipment("Old Grimmorie");
                inventory.put("Silk Robe", 1);
                setEquipment("Silk Robe");
                break;
            default:
                break;
        }
    }
    //public void setgameEnergy(){gameEnergy = 1;}
    //public void setloginEnergy(){loginEnergy = 1;}

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
     * Returns the player's character class.
     *
     * @return The player's character class.
     */
    public String getCharacterClass() {
        return characterClass;
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
     * Gets the player's current experience points.
     * @return The player's current experience points.
     */

    public int getExp() {
        return exp;
    }

    /**
     * Increases the player's level by a given amount.
     */

    public void increaseLevel() {
        level += 1;
        if (level % 5 == 0) {
            abilityPoints++;
        }
        calculateStats();
    }

    public void calculateStats() {
        switch (characterClass){
            case "Warrior" :
                setHP(100 + 50*level);
                setMP(20 + 10*level);
                setSTR(3 + 2*level);
                setDEF(3 + 2*level);
                setSPD(1 + level/2);
                setINT(1 + level/2);
                setLUK(2 + level);
                break;
            case "Archer" :
                setHP(80 + 40*level);
                setMP(30 + 15*level);
                setSTR(2 + level);
                setDEF(1 + level/2);
                setSPD(3 + 2*level);
                setINT(1 + level/2);
                setLUK(3 + 2*level);
                break;
            case "Mage" :
                setHP(70 + 35*level);
                setMP(50 + 25*level);
                setSTR(1 + level/2);
                setDEF(1 + level/2);
                setSPD(2 + level);
                setINT(3 + 2*level);
                setLUK(2 + level);
                break;
            default:
                break;
        }
    }

    public void setEquipment(String item) { //String item should be changed to an Item object.
        // The item should be removed from the inventory and put in the equipment slot.
        // If there is already an item in the slot, it should be unequipped and put back in the inventory.
        // The stats of the unequipped item should be subtracted from equipmentStats and the stats of the equipped item should be added.
    }

    /**
     * Gets the player's current level.
     * @return The player's current level.
     */
    public int getLevel() {return level;}

    public int expNeeded() {
        return (int) (Math.pow(level, 1.5) * 100);
    }

    // return an array with the level of each ability
    public int[] getAbilities () {return abilities;}

    // return the level of one ability
    public int getAbility (int ability) {return abilities[ability];}

    // increase in one level an ability
    public void increaseAbility (int ability) {abilities[ability]++;}

    // return the clan's name of the player
    public String getClanName () {return clanName;}

    // set a new clan
    public void setClanName (String clan) {clanName = clan;}

    public void checkRefresh(){
        long actualTime = System.currentTimeMillis();
        long TIME_BETWEEN_REFRESH = 86400000; // A day in milliseconds.
        //If a day has passed
        if(previousTime + TIME_BETWEEN_REFRESH < actualTime){
            previousTime = actualTime;
            //Refresh energy and login count
            energy = 5;
            loginCount = 0;
            try {
                new PlayerManager().savePlayer(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int getEnergy(){
        checkRefresh();
        return energy;
    }
    public void decreaseEnergy(){
        energy--;
    }
    public int getLoginCount(){
        checkRefresh();
        return loginCount;
    }

    public int getAbilityPoints () {
        return abilityPoints;
    }

    public void decreaseAbilityPoints () {
        abilityPoints--;
    }

    public boolean checkItem(String item) {
        for(String i : inventory.keys()) {
            if(i.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
