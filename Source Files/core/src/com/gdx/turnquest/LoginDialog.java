package com.gdx.turnquest;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class LoginDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;

    public LoginDialog(String title, Runnable runnable, Skin skin) {
        super(title, skin);

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
        button("Login", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;
        if(result) {
            // Check credentials
            // If correct, change screen
            // If incorrect, show error message

        } else {
            hide();
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
}
