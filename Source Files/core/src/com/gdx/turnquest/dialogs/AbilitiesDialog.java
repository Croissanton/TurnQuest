package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.animations.AnimationHandler;

/**
 * A class for showing the abilities while in combat, so you can use them to attack the enemies:
 * see the abilities in combat,
 * select an ability to attack.
 *
 * @author Pablo
 * @author Cristian
 */
public class AbilitiesDialog extends Dialog {

    private final Runnable yesRunnable;

    public AbilitiesDialog(String title, Runnable yesRunnable, Skin skin, Player player, AnimationHandler animationHandler, Enemy enemy) {
        super(title, skin);
        this.yesRunnable = yesRunnable;

        // buttons
        // abilities buttons
        TextButton bAb1 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Pierce");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Root");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Critical");
        }

        TextButton bAb2 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Poison");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Water Stab");
        }

        TextButton bAb3 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Fire Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Multiple");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Wave");
        }

        TextButton bAb4 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Fire Sword");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Ray");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Tear Down");
        }

        // if ability is not unlocked, it will be shown locked
        if (0 == player.getAbility(0) || player.getMP() < 5) {
            bAb1.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        }

        if (0 == player.getAbility(1) || player.getMP() < 8) {
            bAb2.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        }

        if (0 == player.getAbility(2) || player.getMP() < 10) {
            bAb3.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        }

        if (0 == player.getAbility(3) || player.getMP() < 15) {
            bAb4.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        }

        // return button
        TextButton bReturn = new TextButton("Return", skin);

        getContentTable().defaults().pad(10).width(200);
        getContentTable().add(bAb1);
        getContentTable().add(bAb2).row();
        getContentTable().add(bAb3);
        getContentTable().add(bAb4).row();
        getContentTable().add(bReturn);

        bAb1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // if ability is not unlocked, it will be locked
                if (0 == player.getAbility(0) || player.getMP() < 5) {
                    //Play error sound
                } else {
                    CombatLogic.useAbility(player, enemy, 0, 5);
                    animationHandler.setCurrent("air_atk");
                    result(true);
                }
            }
        });

        bAb2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // if ability is not unlocked, it will be locked
                if (0 == player.getAbility(1) || player.getMP() < 8) {
                    //Play error sound
                } else {
                    CombatLogic.useAbility(player, enemy, 1, 8);
                    animationHandler.setCurrent("2_atk");
                    result(true);
                    hide();
                }
            }
        });

        bAb3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // if ability is not unlocked, it will be locked
                if (0 == player.getAbility(2) || player.getMP() < 10) {
                    //play error sound
                } else {
                    CombatLogic.useAbility(player, enemy, 2, 10);
                    animationHandler.setCurrent("3_atk");
                    result(true);
                }
            }
        });

        bAb4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // if ability is not unlocked, it will be locked
                if (0 == player.getAbility(3) || player.getMP() < 15) {
                    //play error sound
                } else {
                    CombatLogic.useAbility(player, enemy, 3, 15);
                    animationHandler.setCurrent("sp_atk");
                    result(true);
                    hide();
                }
            }
        });

        bReturn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                result(false);
                hide();
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
