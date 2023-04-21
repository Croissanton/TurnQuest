package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.screens.GameScreen;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

import static com.gdx.turnquest.TurnQuest.hasInternetConnection;

public class LoginDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    private final Label errorLabel;
    private final TurnQuest game;

    public LoginDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;

        getContentTable().defaults().expand().pad(10);
        getContentTable().add("Username:");
        usernameField = new TextField("", skin);
        getContentTable().add(usernameField).width(400);
        getContentTable().row();
        getContentTable().add("Password:");
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        getContentTable().add(passwordField).width(400);
        getContentTable().row();
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);
        button("Login", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;
        if (result) {
            // Check credentials
            String username = usernameField.getText();
            String password = passwordField.getText();
            // If correct, change screen
            // Check if the credentials are valid
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!isValidCredentials(username, password)) {
                    // If the credentials are not valid, display an error message
                    errorLabel.setText("Invalid username or password.");
                } else {
                    // If the credentials are valid, proceed with the login process
                    hide();
                    game.setScreen(new GameScreen(game));
                }
            }
        }
        else super.hide();
    }

    @Override
    public void hide() {
        // Only hide the dialog if the credentials are valid, this makes it so  that the dialog is not closed whenever a button is pressed but when it needs to.
        if (isValidCredentials(usernameField.getText(), passwordField.getText())) {
            super.hide();
        }
    }

    @Override
    public float getPrefWidth() {
        // Set the preferred width of the dialog
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // Set the preferred height of the dialog
        return 500f;
    }

    // Helper method to check if the credentials are valid
    private boolean isValidCredentials(String username, String password) {
        try {
            FileHandle file = Gdx.files.internal("../Data/" + "players.json");
            String json = file.readString();
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(json);
            JsonValue players = root.get("players");
            for (JsonValue player : players) {
                if(username.equals(player.getString("username"))){
                    return Objects.equals(hashPassword(password), player.getString("password"));
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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
