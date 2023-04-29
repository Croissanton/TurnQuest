package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

public class Item {
    private final String name;
    private final String description;
    private final int value;
    private ObjectMap<String, Integer> stats;
    private final String type;

    private final String classType;

    public Item(int id, String name, String description, int value, ObjectMap<String, Integer> stats, String type, String classType) {
        if(value < 0 || type == "CONSUMABLE" || type == "EQUIPMENT") throw new IllegalArgumentException("Incorrect value or type");
        this.name = name;
        this.description = description;
        this.value = value;
        this.stats = stats;
        this.type = type;
        this.classType = classType; //Can be "Warrior", "Mage", "Archer", "All"
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public ObjectMap<String, Integer> getStats() {
        return stats;
    }

    public String getType() {
        return type;
    }

    public String getClassType() {
        return classType;
    }
}
