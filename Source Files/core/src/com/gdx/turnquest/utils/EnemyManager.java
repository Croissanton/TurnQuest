package com.gdx.turnquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.entities.Enemy;

import java.io.IOException;

public class EnemyManager {
    private static final String ENEMIES_PATH = "../Data/enemies.json";
    private final FileHandle file = Gdx.files.local(ENEMIES_PATH);
    private final Json json = new Json();
    private final ObjectMap<String, Enemy> enemiesData;

    @SuppressWarnings("unchecked")
    public EnemyManager() {
        json.setOutputType(JsonWriter.OutputType.json);
        if(file.exists()) {
            enemiesData = json.fromJson(ObjectMap.class, Enemy.class, file.readString());
        } else {
            throw new IllegalArgumentException("Enemies.json does not exist. Redownload the game.");
        }
    }
    public Enemy getEnemy(String name) {
        if (!enemiesData.containsKey(name)) {
            throw new IllegalArgumentException("Enemy does not exist.");
        }
        return enemiesData.get(name);
    }
}
