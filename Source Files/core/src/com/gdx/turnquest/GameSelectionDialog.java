package com.gdx.turnquest;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import static com.gdx.turnquest.TurnQuest.*;

public class GameSelectionDialog extends Dialog {

    private final TurnQuest game;

    private Runnable yesRunnable;

    public GameSelectionDialog(String title, String message, Runnable yesRunnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;
        this.yesRunnable = yesRunnable;
        text(message);
        button("Yes", true);
        button("No", false);
    }

    @Override
    protected void result(Object object) {
        boolean result = (boolean) object;

        if (result) {
            showLoginDialog();
        } else {
            showLoginDialog();
        }
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