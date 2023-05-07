package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

public class Player extends Character {

    private String characterClass;
    private int gold;
    private int exp;
    private int level;
    private ObjectMap<String, Integer> inventory;
    private final String playerName;

    private String[] equipment = new String[3]; //There should also be a slot for rings.

    private int[] equipmentStats = new int[7]; //The stats that the equipment gives.

    private int nAb1;

    private int nAb2;

    private int nAb3;

    private int nAb4;

    private int gameEnergy;

    private int loginEnergy;

    private String gametimeZero;

    private String logintimeZero;





    public Player() {
        super();
        this.playerName = null;
    }

    //creating a default Player, acts a new player that just began the game
    public Player(String playerName, String characterClass) {
        super();
        this.playerName = playerName;
        this.characterClass = characterClass;
        gameEnergy=0;
        loginEnergy=1;
        logintimeZero="17/04/2023 12:34:00";
        gametimeZero="17/04/2023 12:34:00";
        gold = 0;
        exp = 0;
        level = 1;
        nAb1 = 0;
        nAb2 = 0;
        nAb3 = 0;
        nAb4 = 0;
        inventory = new ObjectMap<>();
        inventory.put("Potion", 5);
        inventory.put("Ether", 5);
        //TODO: add character class handling -> setting stats according to the class given.
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

    public void addItem(String item, int quantity) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + quantity);
        } else {
            inventory.put(item, quantity);
        }
    }

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

    public ObjectMap<String, Integer> getInventory() {
        return inventory;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void addGold(int g) {
        gold += g;
    }

    public int removeGold(int g) {
        if (gold < g) {
            return -1;
        }
        gold -= g;
        return 0;
        // 0 means success, -1 means not enough gold.
    }

    public int getGold() {
        return gold;
    }

    public void setExp(int e) {
        exp = e;
    }

    public int getExp() {
        return exp;
    }

    public void increaseLevel() {
        level += 1;
        calculateStats();
    }

    private void calculateStats() {
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

    public int getLevel() {return level;}

    public int expNeeded() {
        return (int) (Math.pow(level, 1.5) * 100);
    }

    public int getNAb1 () {return nAb1;}
    public void setNAb1 (int n) {nAb1 = n;}

    public int getNAb2 () {return nAb2;}
    public void setNAb2 (int n) {nAb2 = n;}

    public int getNAb3 () {return nAb3;}
    public void setNAb3 (int n) {nAb3 = n;}

    public int getNAb4 () {return nAb4;}
    public void setNAb4 (int n) {nAb4 = n;}
}
