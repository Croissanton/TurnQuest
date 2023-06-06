package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * A class that shows a simple dialog with information.
 *
 * @author Michal
 */

public class InformationDialog extends Dialog {

    public InformationDialog(String title, String message, Skin skin) {
        super(title, skin);
        getTitleLabel().setFontScale(1.6f);
        getTitleLabel().setAlignment(Align.center);
        text(message);

        button("Okay");
    }

    @Override
    protected void result(Object object) {
        hide();
    }

    @Override
    public float getPrefWidth() {
        // Set the preferred width of the dialog
        return 500f;
    }

    @Override
    public float getPrefHeight() {
        // Set the preferred height of the dialog
        return 200f;
    }


}
