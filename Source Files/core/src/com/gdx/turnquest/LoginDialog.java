package com.gdx.turnquest;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LoginDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    private final Label errorLabel;
    private final TurnQuest game;

    public LoginDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;
        getContentTable().defaults().pad(10);
        getContentTable().add("Username:");
        usernameField = new TextField("", skin);
        getContentTable().add(usernameField).width(200);
        getContentTable().row();
        getContentTable().add("Password:");
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        getContentTable().add(passwordField).width(200);
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
            Scanner file = new Scanner(new FileReader("../" + username + ".txt"));
            if (file.nextLine().equals(username) && file.nextLine().equals(password)) {
                return true;
            }
            else{
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
