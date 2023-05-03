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
