package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.screens.MapScreen;

public class GameOverDialog extends Dialog {

    private TurnQuest game;

    public GameOverDialog(TurnQuest game, Skin skin) {
        super("Defeat", skin);
        this.game = game;
        text("You have been defeated.");
        button("OK");
    }

    @Override
    protected void result(Object object) {
        game.pushScreen(new MapScreen(game));
        hide();
    }
}