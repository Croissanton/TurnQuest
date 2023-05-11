package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.screens.MapScreen;

public class VictoryDialog extends Dialog {

    private TurnQuest game;

    public VictoryDialog(TurnQuest game, Skin skin) {
        super("Victory", skin);
        this.game = game;
        text("Congratulations! You have won the combat.");
        button("OK");
    }

    @Override
    protected void result(Object object) {
        game.pushScreen(new MapScreen(game));
        hide();
    }

}