package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.screens.MapScreen;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

public class GameOverDialog extends Dialog {

    private final TurnQuest game;

    public GameOverDialog(TurnQuest game, Skin skin) {
        super("Defeat", skin);
        this.game = game;
        game.getMusic().dispose();
        text("You have been defeated.");
        button("OK");
    }

    @Override
    protected void result(Object object) {
        game.setMusic("intro.ogg");
        game.getCurrentPlayer().calculateStats();
        try {
            new PlayerManager().savePlayer(game.getCurrentPlayer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game.popScreen();
        hide();
    }
}