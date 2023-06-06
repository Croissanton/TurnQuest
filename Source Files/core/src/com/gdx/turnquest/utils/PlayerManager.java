package com.gdx.turnquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.entities.Player;
import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.hasInternetConnection;
import static java.lang.Thread.sleep;

/**
 *
 * The PlayerManager class manages the data of players in the game. It stores and retrieves
 * player data to/from a JSON file. It uses LibGDX's file handling and JSON utilities to manage
 * the data. This class provides methods to add, retrieve, and remove player data from the
 * JSON file.
 *
 * @author Ignacy
 */

public class PlayerManager {
    private static final String PLAYERS_PATH = "../Data/players.json";
    private final FileHandle file = Gdx.files.local(PLAYERS_PATH);
    private final Json json = new Json();
    private ObjectMap<String, Player> playersData;

    /**
     * Constructs a PlayerManager object. It initializes the file handle and the JSON object
     * for storing and retrieving player data. If the JSON file already exists, the player
     * data is loaded from the file. Otherwise, a new JSON file is created and an empty player
     * data map is created.
     */

    public PlayerManager() throws IOException {
        json.setOutputType(JsonWriter.OutputType.json);
        if(!file.file().createNewFile()) {
            playersData = json.fromJson(ObjectMap.class, Player.class, file.readString());
            if(playersData == null) playersData = new ObjectMap<>();
        } else {
            playersData = new ObjectMap<>();
        }
    }

    /**
     * Retrieves the player data for a given username.
     *
     * @param username The username of the player.
     * @return The Player object representing the player data.
     * @throws IllegalArgumentException If the player data for the given username does not exist.
     */

    public Player getPlayer(String username) {
        if (!playersData.containsKey(username)) {
            throw new IllegalArgumentException("Player does not exist.");
        }
        return playersData.get(username);
    }

    /**
     * Adds a new player to the player data map and writes the updated player data map to the
     * JSON file.
     *
     * @param player The Player object representing the player to be added.
     * @throws IllegalArgumentException If player data for the given player already exists.
     */

    public void addPlayer(Player player) {
        if (playersData.containsKey(player.getPlayerName())) {
            throw new IllegalArgumentException("Player already exists.");
        }
        playersData.put(player.getPlayerName(), player);
        file.writeString(json.prettyPrint(playersData), false);
    }

    /**
     * Updates the player data for a given player data and writes the
     * updated player data map to the JSON file.
     *
     * @param player The Player object representing the player to be added.
     * @throws InterruptedException because of the sleep method.
     */
    public int savePlayer(Player player){
        int cont = 0;
        while(!hasInternetConnection()) {
            if(cont == 5){
                return -1; // ERROR, COULD NOT SAVE, SHOULD TREAT THIS.
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++cont;
        }
        removePlayer(player.getPlayerName());
        addPlayer(player);
        return 0;
    }
    /**
     * Removes the player data for a given username from the player data map and writes the
     * updated player data map to the JSON file.
     *
     * @param username The username of the player to be removed.
     * @throws IllegalArgumentException If player data for the given username does not exist.
     */
    public void removePlayer(String username) {
        if (!playersData.containsKey(username)) {
            throw new IllegalArgumentException("Player does not exist.");
        }
        playersData.remove(username);
        file.writeString(json.prettyPrint(playersData), false);
    }

    public boolean checkPlayerName(String playerName) {
        return playersData != null && playerName != null && playersData.containsKey(playerName);
    }
}
