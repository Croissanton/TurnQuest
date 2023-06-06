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


/**
 * This class represents a user manager that stores and manages user data for authentication.
 * It utilizes a JSON file to store and retrieve user data.
 * This class provides functionality for adding a new user, checking if a user exists, and authenticating a user.
 * Passwords are hashed for security purposes using the SHA-256 algorithm.
 *
 * @author Ignacy
 */

public class UserManager {
    private final ObjectMap<String, String> usersData;
    private static final String USERS_PATH = "../Data/users.json";
    private final FileHandle file = Gdx.files.local(USERS_PATH);
    private final Json json = new Json();


    /**
     * Constructs a new instance of the UserManager class.
     * If the JSON file exists, the user data is loaded from it.
     * Otherwise, an empty object map is created.
     */

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

    /**
     * Adds a new user to the user data.
     * If the username already exists, an IllegalArgumentException is thrown.
     * The user's password is hashed for security purposes.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     */

    public void addUser(String username, String password) {
        //Check if the user already exists.
        if (checkUsername(username)) {
            throw new IllegalArgumentException("User already exists.");
        }
        usersData.put(username, hashPassword(password));
        file.writeString(json.toJson(usersData), false);
    }

    /**
     * Checks if the given username and password match any existing user data.
     * Returns true if a match is found, false otherwise.
     *
     * @param username the username to check
     * @param password the password to check
     * @return true if the username and password match an existing user, false otherwise
     */

    public boolean checkUser(String username, String password) {
        if (!checkUsername(username)) {
            return false;
        }
        return usersData.get(username).equals(hashPassword(password));
    }

    /**
     * Checks if the given username exists in the user data.
     * Returns true if the username exists, false otherwise.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */

    public boolean checkUsername(String username) {
        return usersData.containsKey(username);
    }

    /**
     * Hashes the given password using the SHA-256 algorithm and returns the result as a Base64-encoded string.
     *
     * @param password the password to hash
     * @return the hashed password as a Base64-encoded string
     */

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

