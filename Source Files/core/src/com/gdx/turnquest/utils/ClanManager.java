package com.gdx.turnquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.turnquest.entities.Clan;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.hasInternetConnection;
import static java.lang.Thread.sleep;

/**
 * This class represents a clan manager that stores and manages clan data.
 * It utilizes a JSON file to store and retrieve clan data.
 * This class provides functionality for adding a new clan and checking if a clan exists.
 * It also provides functionality for deleting a clan.
 *
 * @author Pablo
 * @author Cristian
 */

public class ClanManager {
    private ObjectMap<String, Clan> clansData;
    private static final String CLANS_PATH = "../Data/clans.json";
    private final FileHandle file = Gdx.files.local(CLANS_PATH);
    private final Json json = new Json();


    /**
     * Constructs a new instance of the ClanManager class.
     * If the JSON file exists, the clan data is loaded from it.
     * Otherwise, an empty object map is created.
     */

    public ClanManager() {
        json.setOutputType(JsonWriter.OutputType.json);
        try {
            if(!file.file().createNewFile()) {
                clansData = json.fromJson(ObjectMap.class, file.readString());
                if(clansData == null) clansData = new ObjectMap<>();
            } else {
                clansData = new ObjectMap<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new clan to the clan data map and writes the updated clan data map to the
     * JSON file.
     *
     * @param clan The clan object representing the clan to be added.
     * @throws IllegalArgumentException If clan data for the given clan already exists.
     */

    public void addClan(Clan clan) {
        if (checkClanName(clan.getName())) {
            throw new IllegalArgumentException("Clan already exists.");
        }
        clansData.put(clan.getName(), clan);
        file.writeString(json.prettyPrint(clansData), false);
    }

    /**
     * Checks if the given clan name exists in the user data.
     * Returns true if the clan name exists, false otherwise.
     *
     * @param clanName the clan name to check
     * @return true if the clan name exists, false otherwise
     */

    public boolean checkClanName(String clanName) {
        return clansData != null && clansData.containsKey(clanName);
    }

    /**
     * Retrieves the clan data for a given name.
     *
     * @param clanName The name of the clan.
     * @return The Clan object representing the clan data.
     * @throws IllegalArgumentException If the clan data for the given name does not exist.
     */

    public Clan getClan(String clanName) {
        if (!checkClanName(clanName)) {
            throw new IllegalArgumentException("Clan does not exist.");
        }
        return clansData.get(clanName);
    }

    /**
     * Removes the clan data for a given name from the clan data map.
     *
     * @param clanName The name of the clan to be removed.
     * @throws IllegalArgumentException If clan data for the given name does not exist.
     */
    public void removeClan(String clanName) {
        if (!checkClanName(clanName)) {
            throw new IllegalArgumentException("Clan does not exist.");
        }
        clansData.remove(clanName);
        file.writeString(json.prettyPrint(clansData), false);
    }

    public int save(Clan clan) {
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
        removeClan(clan.getName());
        addClan(clan);
        return 0;
    }
}