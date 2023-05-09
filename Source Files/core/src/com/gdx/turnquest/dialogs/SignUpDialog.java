package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.GameScreen;
import com.gdx.turnquest.utils.PlayerManager;
import com.gdx.turnquest.utils.UserManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import static com.gdx.turnquest.TurnQuest.*;

public class SignUpDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    final CheckBox Warrior;
    final CheckBox Archer;
    final CheckBox Mage;
    private Label errorLabel;
    private final TurnQuest game;
    private String username;
    private String password;
    private String characterClass;
    private boolean success = false;
    private static final String FILE_PATH = "../Data/players.json";


    public SignUpDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;

        // create check boxes
        Warrior = new CheckBox("Warrior", skin);
        Archer = new CheckBox("Archer", skin);
        Mage = new CheckBox("Mage", skin);

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
        getContentTable().add(Mage).row();

        // errors
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);
        button("Create", true);
        button("Cancel", false);

        Warrior.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterClass = "Warrior";
                if (Archer.isChecked()) Archer.setChecked(false);
                if (Mage.isChecked()) Mage.setChecked(false);
            }
        });

        Archer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterClass = "Archer";
                if (Warrior.isChecked()) Warrior.setChecked(false);
                if (Mage.isChecked()) Mage.setChecked(false);
            }
        });

        Mage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterClass = "Mage";
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
            //TODO: checking if the user has connection to the database and check for the username there.
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                // check username
                UserManager userManager = new UserManager();
                if (userManager.checkUsername(username)) {
                    errorLabel.setText("Invalid username, it already exists.");
                } else if (4 > password.length()) {
                    errorLabel.setText("Invalid password, it needs to have 4 characters.");
                } else if (!Warrior.isChecked() && !Archer.isChecked() && !Mage.isChecked()) {
                    errorLabel.setText("Please select a class.");
                } else {
                    try {
                        userManager.addUser(username, password);
                    } catch (IllegalArgumentException e) {
                        errorLabel.setText("Username already exists. Try logging in.");
                    }
                    Player player = new Player (username, characterClass);
                    //add the player to the database (json for now)
                    PlayerManager playerManager;
                    try {
                        playerManager = new PlayerManager();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    playerManager.addPlayer(player);
                    // set the player in the TurnQuest class
                    game.setCurrentPlayer(player);
                    // hide the sign up dialog and go to game screen
                    success = true;
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
        if (success) {
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

    private boolean freeUsername(String username) {
        boolean free = true;
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                free = false;
                file.createNewFile();
            } else {
                //search the json dictionary for the username
                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(new FileReader(FILE_PATH));
                if (object.containsKey(username)) free = false;
            }
        } catch (IOException e) {
            System.err.println("ERROR: no text written");
            free = false;
        } catch (ParseException e) {
            System.err.print("ERROR: could not parse JSON file");
            free = false;
        }
        return free;
    }
}