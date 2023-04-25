package com.gdx.turnquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

//TODO: Add functionality when the player is not existent in the JSON file, add when they are already there.
//TODO: Come up with the best way to send player data to the database. JSON file is currently temporary.

//We can't send JSON with all players to the database, because it wouldn't make sense to have all the players in one JSON file.
//We probably can send a JSON file for EACH player, but that would be a lot of files.
//Since each player has a set of data with names that don't change, we can use SQL to store the data.
//We can use the username as the primary key, and the rest of the data as the values.

public class UserManager {
    private ObjectMap<String, String> usersData;
    private static final String USERS_PATH = "../Data/users.json";
    private FileHandle file = Gdx.files.local(USERS_PATH);
    private Json json = new Json();


    public UserManager() {
        if(file.exists()) {
            usersData = json.fromJson(ObjectMap.class, file.readString());
        } else {
            try {
                file.file().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            usersData = new ObjectMap<>();
        }
    }

    public void addUser(String username, String password) {
        //Check if the user already exists.
        if (checkUsername(username)) {
            throw new IllegalArgumentException("User already exists.");
        }
        usersData.put(username, hashPassword(password));
        file.writeString(json.toJson(usersData), false);
    }

    public boolean checkUser(String username, String password) {
        if (!checkUsername(username)) {
            return false;
        }
        return usersData.get(username).equals(hashPassword(password));
    }

    public boolean checkUsername(String username) {
        return usersData.containsKey(username);
    }

    protected static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}

