package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;

import static com.gdx.turnquest.TurnQuest.*;

public class LevelUpDialog extends Dialog {

    private final TurnQuest game;

    public LevelUpDialog(TurnQuest game, Skin skin, int level) {
        super("Level up!", skin);
        game.getMusic().dispose();
        this.game = game;
        text("Congratulations! You have leveled up." + level + " -> " + game.getCurrentPlayer().getLevel());
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