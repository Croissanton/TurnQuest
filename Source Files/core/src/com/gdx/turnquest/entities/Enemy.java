package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

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
