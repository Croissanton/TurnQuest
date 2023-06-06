package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gdx.turnquest.TurnQuest;

/**
 * A class that is responsible for showing a dialog
 * that lets the player choose between sign up or log in.
 *
 * @author Pablo
 */

public class GameSelectionDialog extends Dialog {

    private final TurnQuest game;

    private Runnable yesRunnable;

    public GameSelectionDialog(String title, String message, Runnable yesRunnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;
        this.yesRunnable = yesRunnable;
        text(message);
        button("Sign up", true);
        button("Log in", false);
    }

    @Override
    protected void result(Object object) {
        boolean result = (boolean) object;

        if (result) {
            showSignUpDialog();
        } else {
            showLoginDialog();
        }
    }

    private void showSignUpDialog() {
        SignUpDialog dialog = new SignUpDialog("SignUp", new Runnable() {
            @Override
            public void run() {

            }
        }, getSkin(), game);
        dialog.show(getStage());
    }

    private void showLoginDialog() {
        LoginDialog dialog = new LoginDialog("Login", new Runnable() {
            @Override
            public void run() {
                // Handle login here
            }
        }, getSkin(), game);
        dialog.show(getStage());
    }
}