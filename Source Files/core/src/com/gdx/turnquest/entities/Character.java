package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;


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
//    debugging purposes
    public Character (int debugNum){
        stats.put("HP", debugNum);
        stats.put("MP", debugNum);
        stats.put("STR", debugNum);
        stats.put("DEF", debugNum);
        stats.put("SPD", debugNum);
        stats.put("INT", debugNum);
        stats.put("LUK", debugNum);
    }
    /* TODO: add character class handling -> setting stats according to the class given.
        Each class will have different stats, so we need to handle that.
        Also, we need to handle the level up system, which will increase the stats of the character.
    */

    public ObjectMap<String, Integer> getStats(){
        return stats;
    }

    public void setStats(ObjectMap<String, Integer> stats){
        this.stats = stats;
    }

    public int getHP(){
        return stats.get("HP");
    }

    public void increaseHP(int hp){
        stats.put("HP", stats.get("HP") + hp);
    }

    public void setHP(int hp) { stats.put("HP", hp);}

    public int getMP(){
        return stats.get("MP");
    }

    public void increaseMP(int mp) {stats.put("MP", stats.get("MP") + mp);}

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
