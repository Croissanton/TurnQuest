package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;

import static com.gdx.turnquest.TurnQuest.*;

public class VictoryDialog extends Dialog {

    private final TurnQuest game;
    private final int previousLevel;

    public VictoryDialog(TurnQuest game, Skin skin, int previousLevel) {
        super("Victory", skin);
        this.previousLevel = previousLevel;
        game.getMusic().dispose();
        this.game = game;
        text("Congratulations! You have won the combat.");
        playSfx("victory.ogg");
        button("OK");
    }

    @Override
    protected void result(Object object) {
        if(previousLevel < game.getCurrentPlayer().getLevel()){
            new LevelUpDialog(game, Assets.getSkin(), previousLevel).show(game.getStage());
        }
        else {
            game.getCurrentPlayer().calculateStats();
            game.setMusic("intro.ogg");
            game.popScreen();
        }
        hide();
    }

}