package com.gdx.turnquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.entities.Player;
import java.io.IOException;

public class PlayerManager {
    private static final String PLAYERS_PATH = "../Data/players.json";
    private final FileHandle file = Gdx.files.local(PLAYERS_PATH);
    private final Json json = new Json();
    private final ObjectMap<String, Player> playersData;

    @SuppressWarnings("unchecked")
    public PlayerManager() {
        json.setOutputType(JsonWriter.OutputType.json);
        if(file.exists()) {
            playersData = json.fromJson(ObjectMap.class, Player.class, file.readString());
        } else {
            try {
                file.file().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playersData = new ObjectMap<>();
        }
    }
    public Player getPlayer(String username) {
        if (!playersData.containsKey(username)) {
            throw new IllegalArgumentException("Player does not exist.");
        }
        return playersData.get(username);
    }


    public void addPlayer(Player player) {
        if (playersData.containsKey(player.getPlayerName())) {
            throw new IllegalArgumentException("Player already exists.");
        }
        playersData.put(player.getPlayerName(), player);
        file.writeString(json.prettyPrint(playersData), false);
    }

    public void removePlayer(String username) {
        if (!playersData.containsKey(username)) {
            throw new IllegalArgumentException("Player does not exist.");
        }
        playersData.remove(username);
        file.writeString(json.prettyPrint(playersData), false);
    }
}
