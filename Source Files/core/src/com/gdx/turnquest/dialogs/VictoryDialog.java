package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;

/**
 * This class is responsible for showing a victory dialog.
 * It shows a message indicating that the player has won the combat.
 * It also shows a level up dialog if the player has leveled up.
 *
 * @author Cristian
 */
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
            try {
                new PlayerManager().savePlayer(game.getCurrentPlayer());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            game.setMusic("intro.ogg");
            game.popScreen();
        }
        hide();
    }

}