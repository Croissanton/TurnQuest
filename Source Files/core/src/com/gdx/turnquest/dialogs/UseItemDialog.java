package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.entities.Character;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.animations.AnimationHandler;

import java.util.Arrays;

/**
 * This class shows a dialog that allows the player to use an item in combat.
 * The dialog is shown when the player clicks on the "Item" button in combat.
 * This class is used in the CombatScreen and BossScreen classes.
 *
 * @author Cristian
 */

public class UseItemDialog extends Dialog {

    private final Runnable yesRunnable;

    public UseItemDialog(String title, Runnable yesRunnable, Skin skin, TurnQuest game, Player player) {
        super(title, skin);
        this.yesRunnable = yesRunnable;
        boolean[] available = new boolean[6];
        Arrays.fill(available, true);

        // buttons
        // abilities buttons
        TextButton bPotion = new TextButton("Potion", Assets.getSkin());
        TextButton bPotionp = new TextButton("Potion+", Assets.getSkin());
        TextButton bPotionpp = new TextButton("Potion++", Assets.getSkin());
        TextButton bEther = new TextButton("Ether", Assets.getSkin());
        TextButton bEtherp = new TextButton("Ether+", Assets.getSkin());
        TextButton bEtherpp = new TextButton("Ether++", Assets.getSkin());


        // if ability is not unlocked, it will be shown locked
        if (!game.getCurrentPlayer().checkItem("Potion")) {
            bPotion.setColor(0.3f, 0.7f, 0.8f, 0.5f);
            available[0] = false;
        }

        if (!game.getCurrentPlayer().checkItem("Potion +")) {
            bPotionp.setColor(0.3f, 0.7f, 0.8f, 0.5f);
            available[1] = false;
        }

        if (!game.getCurrentPlayer().checkItem("Potion ++")) {
            bPotionpp.setColor(0.3f, 0.7f, 0.8f, 0.5f);
            available[2] = false;
        }
        if(!game.getCurrentPlayer().checkItem("Ether")){
            bEther.setColor(0.3f, 0.7f, 0.8f, 0.5f);
            available[3] = false;
        }
        if(!game.getCurrentPlayer().checkItem("Ether +")){
            bEtherp.setColor(0.3f, 0.7f, 0.8f, 0.5f);
            available[4] = false;
        }
        if(!game.getCurrentPlayer().checkItem("Ether ++")){
            bEtherpp.setColor(0.3f, 0.7f, 0.8f, 0.5f);
            available[5] = false;
        }


        // return button
        TextButton bReturn = new TextButton("Return", skin);

        getContentTable().defaults().pad(10).width(200);
        getContentTable().add(bPotion);
        getContentTable().add(bEther).row();
        getContentTable().add(bPotionp);
        getContentTable().add(bEtherp).row();
        getContentTable().add(bPotionpp);
        getContentTable().add(bEtherpp).row();
        getContentTable().add(bReturn);

        bPotion.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(available[0]) {
                    CombatLogic.useItem(player, "Potion");
                    game.getCurrentPlayer().removeItem("Potion", 1);
                    result(true);
                }
            }
        });
        bPotionp.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(available[1]) {
                    CombatLogic.useItem(player, "Potion +");
                    game.getCurrentPlayer().removeItem("Potion +", 1);
                    result(true);
                }
            }
        });
        bPotionpp.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(available[2]) {
                    CombatLogic.useItem(player, "Potion ++");
                    game.getCurrentPlayer().removeItem("Potion ++", 1);
                    result(true);
                }
            }
        });
        bEther.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(available[3]) {
                    CombatLogic.useItem(player, "Ether");
                    game.getCurrentPlayer().removeItem("Ether", 1);
                    result(true);
                }
            }
        });
        bEtherp.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(available[4]) {
                    CombatLogic.useItem(player, "Ether +");
                    game.getCurrentPlayer().removeItem("Ether +", 1);
                    result(true);
                }
            }
        });
        bEtherpp.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(available[5]) {
                    CombatLogic.useItem(player, "Ether ++");
                    game.getCurrentPlayer().removeItem("Ether ++", 1);
                    result(true);
                }
            }
        });

        bReturn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                result(false);
            }
        });
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
