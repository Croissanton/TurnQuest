package com.gdx.turnquest;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.io.*;
import java.util.Scanner;

public class SignUpDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    private Label errorLabel;
    private final TurnQuest game;
    private String username;
    private String password;

    public SignUpDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
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
        button("Create", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        username = usernameField.getText();
        password = passwordField.getText();

        // Handle the result of the dialog
        boolean result = (boolean) object;

        if (result) {
            // check username
            if (!freeUsername(username)) {
                errorLabel.setText("Invalid username, it already exists.");
            } else if (4 > password.length()) {
                errorLabel.setText("Invalid password, it needs to be at least 4 characters long.");
            } else {
                // create the new file
                createFile();

                // hide te sign up dialog and go to game screen
                hide();
                game.setScreen(new GameScreen(game));
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
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // Set the preferred height of the dialog
        return 500f;
    }

    // to create the file
    private void createFile() {
        String fileName = username + ".txt";
        String filePath = "../";

        //create the file
        File file = new File(filePath + fileName);

        try {
            // write the user and password
            FileWriter writer = new FileWriter(file);

            writer.write(username + "\n");
            writer.write(password + "\n");

            writer.close();

        } catch (IOException e) {
            System.err.println("ERROR: no text written");
            System.err.println("ERROR: no text written");
        }
    }

    private boolean freeUsername(String username) {
        try {
            Scanner file = new Scanner(new FileReader("../" + username + ".txt"));
            return false;
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: username has not been searched");
            return true;
        }
    }
}