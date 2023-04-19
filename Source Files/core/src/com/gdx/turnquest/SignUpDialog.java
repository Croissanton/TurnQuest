package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.*;
import java.util.Scanner;

import static com.gdx.turnquest.LoginDialog.hashPassword;
import static com.gdx.turnquest.TurnQuest.hasInternetConnection;

public class SignUpDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    final CheckBox Warrior;
    final CheckBox Archer;
    final CheckBox Assassin;
    private Label errorLabel;
    private final TurnQuest game;
    private String username;
    private String password;

    public SignUpDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;

        // create check boxes
        Warrior = new CheckBox("Warrior", skin);
        Archer = new CheckBox("Archer", skin);
        Assassin = new CheckBox("Assassin", skin);

        // table
        getContentTable().defaults().expand().pad(10);

        // username field
        getContentTable().add("Username:");
        usernameField = new TextField("", skin);
        getContentTable().add(usernameField).width(400).right().row();

        // password field
        getContentTable().add("Password:");
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        getContentTable().add(passwordField).width(400).right().row();

        // checkboxes
        getContentTable().add(Warrior);
        getContentTable().add(Archer);
        getContentTable().add(Assassin);

        // errors
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);
        button("Create", true);
        button("Cancel", false);

        Warrior.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Archer.isChecked()) Archer.setChecked(false);
                if (Assassin.isChecked()) Assassin.setChecked(false);
            }
        });
        Archer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Warrior.isChecked()) Warrior.setChecked(false);
                if (Assassin.isChecked()) Assassin.setChecked(false);
            }
        });
        Assassin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Archer.isChecked()) Archer.setChecked(false);
                if (Warrior.isChecked()) Warrior.setChecked(false);
            }
        });
    }

    @Override
    protected void result(Object object) {
        username = usernameField.getText();
        password = passwordField.getText();

        // Handle the result of the dialog
        boolean result = (boolean) object;

        if (result) {
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                // check username
                if (!freeUsername(username)) {
                    errorLabel.setText("Invalid username, it already exists.");
                } else if (4 > password.length()) {
                    errorLabel.setText("Invalid password, it needs to have 4 characters.");
                } else {
                    // create the new file
                    addPlayer();
                    // hide te sign up dialog and go to game screen
                    hide();
                    game.setScreen(new GameScreen(game));
                }
            }
        } else {
            super.hide();
        }
    }

    @Override
    public void hide() {
        // Only hide the dialog if the credentials are valid, this makes it so  that the dialog is not closed whenever a button is pressed but when it needs to.
        if (freeUsername(usernameField.getText()) && password.length() >= 4) {
            super.hide();
        }
    }

    @Override
    public float getPrefWidth() {
        // Set the preferred width of the dialog
        return 1000f;
    }

    @Override
    public float getPrefHeight() {
        // Set the preferred height of the dialog
        return 600f;
    }

    // to create the file
    private void addPlayer() {

        try {
            FileHandle file = Gdx.files.internal("../Data/" + "players.json");
            String json = file.readString();
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(json);
            JsonValue players = root.get("players");

        } catch (Exception e) {

        }

        /*
        String fileName = username + ".txt";
        String filePath = "../";

        //create the file
        File file = new File(filePath + fileName);

        try {
            // write the user and password
            FileWriter writer = new FileWriter(file);

            writer.write(username + "\n");
            writer.write(hashPassword(password) + "\n");

            if (true == Warrior.isChecked()) {
                writer.write("Warrior\n");
            } else if (true == Archer.isChecked()) {
                writer.write("Archer\n");
            } else {
                writer.write("Assassin\n");
            }

            writer.close();

        } catch (IOException e) {
            System.err.println("ERROR: no text written");
            System.err.println("ERROR: no text written");
        }
        */
    }

    private boolean freeUsername(String username) {
        try {
            FileHandle file = Gdx.files.internal("../Data/" + "players.json");
            String json = file.readString();
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(json);
            JsonValue players = root.get("players");

            for (JsonValue player : players) {
                if (username.equals(player.getString("username"))) {
                    return false;
                }
            }
        } catch (Exception e) {
            return true;
        }

        return true;
    }
}