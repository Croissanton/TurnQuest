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
    public Enemy(int exp, int gold, String name, ObjectMap<String, Integer> stats) {
        super();
        this.exp = exp;
        this.gold = gold;
        this.name = name;
        super.setStats(stats);
    }
}
