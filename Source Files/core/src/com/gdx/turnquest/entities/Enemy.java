package com.gdx.turnquest.entities;

/**
 * This class represents an enemy in the game.
 * An enemy is a character that the player can fight.
 * An enemy has a set of stats that are used to determine the outcome of battles.
 * The stats are: HP, MP, STR, DEF, SPD, INT, LUK. Same as in Character
 * This class provides methods to get the stats.
 * This class is a child of the Character class.
 *
 * @author Cristian
 */

public class Enemy extends Character {
    private final int exp;
    private final int gold;
    private final String name;

    public Enemy(){
        super();
        exp = 0;
        gold = 0;
        name = "";
    }
    public Enemy(int exp, int gold, String name) {
        super();
        this.exp = exp;
        this.gold = gold;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public int getExp() {
        return exp;
    }
}
