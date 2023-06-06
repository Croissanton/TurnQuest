package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * This class represents a character in the game.
 * A character has a set of stats that are used to determine the outcome of battles.
 * The stats are: HP, MP, STR, DEF, SPD, INT, LUK.
 * HP: Health Points - The amount of damage a character can take before dying.
 * MP: Mana Points - The amount of magic a character can use before running out.
 * STR: Strength - The amount of physical damage a character can deal.
 * DEF: Defense - The amount of physical damage a character can resist.
 * SPD: Speed - The amount of speed a character has in battle.
 * INT: Intelligence - The amount of magic damage a character can deal.
 * LUK: Luck - The amount of luck a character has in battle.
 * This class provides methods to get and set the stats.
 * This class is parent to the Player and Enemy class.
 *
 * @author Cristian
 *
 */

public class Character {
    private ObjectMap<String, Integer> stats = new ObjectMap<>();

    public Character () {
        stats.put("HP", 0);
        stats.put("MP", 0);
        stats.put("STR", 0);
        stats.put("DEF", 0);
        stats.put("SPD", 0);
        stats.put("INT", 0);
        stats.put("LUK", 0);
    }

    public ObjectMap<String, Integer> getStats(){
        return stats;
    }

    public void setStats(ObjectMap<String, Integer> stats){
        this.stats = stats;
    }

    public int getHP(){
        return stats.get("HP");
    }

    public void setHP(int hp) { stats.put("HP", hp);}

    public int getMP(){
        return stats.get("MP");
    }

    public void setMP(int mp) { stats.put("MP", mp);}

    public int getSTR(){
        return stats.get("STR");
    }

    public void setSTR(int str) {stats.put("STR", str);}

    public int getDEF(){
        return stats.get("DEF");
    }

    public void setDEF(int def) {stats.put("DEF", def);}

    public int getSPD(){
        return stats.get("SPD");
    }

    public void setSPD(int spd) {stats.put("SPD", spd);}

    public int getINT(){
        return stats.get("INT");
    }

    public void setINT(int intel) {stats.put("INT", intel);}

    public int getLUK(){
        return stats.get("LUK");
    }

    public void setLUK(int luk) {stats.put("LUK", luk);}
}
