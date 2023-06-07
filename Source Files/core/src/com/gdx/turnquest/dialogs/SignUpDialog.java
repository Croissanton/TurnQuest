package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.GameScreen;
import com.gdx.turnquest.utils.PlayerManager;
import com.gdx.turnquest.utils.UserManager;

import java.io.*;

import static com.gdx.turnquest.TurnQuest.*;

/**
 * This class is responsible for showing a sign up dialog.
 * It asks for a username and password.
 * Then it checks if the credentials are correct.
 * If they are, it creates a new user and player.
 * If not, it shows an error message.
 * It also asks for a character class.
 *
 * @author Pablo
 * @author Ignacy
 * @author Cristian
 */

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

        //create tooltips
        Tooltip<Label> warriorTooltip = new Tooltip<>(new Label("Warrior is a melee class.\nSurvivability is its prime characteristic.", Assets.getSkin()));
        warriorTooltip.setInstant(true);
        Tooltip<Label> archerTooltip = new Tooltip<>(new Label("Archer is a ranged class that uses bows and arrows.\nArchers are fast and are prone to be lucky.", Assets.getSkin()));
        archerTooltip.setInstant(true);
        Tooltip<Label> mageTooltip = new Tooltip<>(new Label("Mage is a magic class that casts spells.\nIts mana and intelligence stats are the highest of all classes. ", Assets.getSkin()));
        mageTooltip.setInstant(true);
        Warrior.addListener(warriorTooltip);
        Archer.addListener(archerTooltip);
        Mage.addListener(mageTooltip);

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
                    game.pushScreen(new GameScreen(game));
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
}