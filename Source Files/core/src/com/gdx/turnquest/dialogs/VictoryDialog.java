package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.screens.MapScreen;

import static com.gdx.turnquest.TurnQuest.*;

public class VictoryDialog extends Dialog {

    private TurnQuest game;

    public VictoryDialog(TurnQuest game, Skin skin) {
        super("Victory", skin);
        game.getMusic().dispose();
        this.game = game;
        text("Congratulations! You have won the combat.");
        playSfx("victory.ogg");
        button("OK");
    }

    @Override
    protected void result(Object object) {
        game.setMusic("intro.ogg");
        game.popScreen();
        hide();
    }

}