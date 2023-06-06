package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A class that is responsible for showing a simple confirmation dialog.
 *
 * @author Cristian
 */

public class ConfirmationDialog extends Dialog {

    private Runnable yesRunnable;

    public ConfirmationDialog(String title, String message, Runnable yesRunnable, Skin skin) {
        super(title, skin);
        this.yesRunnable = yesRunnable;
        text(message);
        button("Yes", true);
        button("No", false);
    }

    @Override
    protected void result(Object object) {
        boolean result = (boolean) object;
        if (result) {
            yesRunnable.run();
        }
        hide();
    }
}
