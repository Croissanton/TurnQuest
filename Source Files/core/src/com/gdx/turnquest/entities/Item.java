package com.gdx.turnquest.entities;

import com.badlogic.gdx.utils.ObjectMap;

public class Item {
    private final String name;
    private final String description;
    private final int value;
    private ObjectMap<String, Integer> stats;
    private final String type;
    private final String classType;
    private final String imagePath;

    public Item(String name, String description, int value, ObjectMap<String, Integer> stats, String type, String classType, String imagePath) {
        if(value < 0) throw new IllegalArgumentException("Invalid value.");
        if(stats.isEmpty() || stats.size > 7) throw new IllegalArgumentException("Invalid stats.");
        if(!type.equals("CONSUMABLE") && !type.equals("WEAPON_1") && !type.equals("WEAPON_2") && !type.equals("ARMOR") && !type.equals("RING")) throw new IllegalArgumentException("Invalid type.");
        if(!classType.equals("Warrior") && !classType.equals("Archer") && !classType.equals("Mage") && !classType.equals("All")) throw new IllegalArgumentException("Invalid class type.");
        this.name = name;
        this.description = description;
        this.value = value;
        this.stats = stats;
        this.type = type;
        this.imagePath = imagePath;
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
