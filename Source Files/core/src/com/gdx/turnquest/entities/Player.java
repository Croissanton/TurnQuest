package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

public class Player extends Character {

    private String characterClass;
    private int gold;
    private int exp;
    private int level;
    private ObjectMap<String, Integer> inventory;
    private final String playerName;


    public Player() {
        super();
        this.playerName = null;
    }

    //creating a default Player, acts a new player that just began the game
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

    public void setInventory(ObjectMap<String, Integer> inv) {
        inventory = inv;
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

    public void setCharacterClass(String cc) {
        characterClass = cc;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setGold(int g) {
        gold = g;
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

    public void addExp(int e) {
        exp += e;
    }

    public int getExp() {
        return exp;
    }

    public void setLevel(int l) {level = l;}

    public void increaseLevel(int l) {level += l;}

    public int getLevel() {return level;}

    public int expNeeded() {
        return (int) (Math.pow(level, 1.5) * 100);
    }
}
