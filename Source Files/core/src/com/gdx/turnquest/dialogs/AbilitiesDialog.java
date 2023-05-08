package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.utils.EnemyManager;
import com.gdx.turnquest.screens.CombatScreen;

public class AbilitiesDialog extends Dialog {

    private final TurnQuest game;
    private Runnable yesRunnable;

    public AbilitiesDialog(String title, String message, Runnable yesRunnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;
        Player player = game.getCurrentPlayer();
        Enemy enemy = new EnemyManager().getEnemy("enemy1_01");
        this.yesRunnable = yesRunnable;

        // buttons
        // abilities buttons
        TextButton bAb1 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Pierce");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Root");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Critical");
        }

        TextButton bAb2 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Poison");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Water Stab");
        }

        TextButton bAb3 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Fire Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Multiple");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Wave");
        }

        TextButton bAb4 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Fire Sword");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Ray");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Tear Down");
        }

        // return button
        TextButton bReturn = new TextButton("Return", skin);

        getContentTable().defaults().pad(10).width(200);
        getContentTable().add(bAb1);
        getContentTable().add(bAb2).row();
        getContentTable().add(bAb3);
        getContentTable().add(bAb4).row();
        getContentTable().add(bReturn);

        bAb1.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.pierceAttack(player, CombatScreen.getEnemy());
                } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.rootAttack(player, CombatScreen.getEnemy());
                } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.criticalAttack(player, CombatScreen.getEnemy());
                }

                hide();
            }
        });

        bAb2.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.spinAttack(player, CombatScreen.getEnemy());
                } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.poisonAttack(player, CombatScreen.getEnemy());
                } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.waterStabAttack(player, CombatScreen.getEnemy());
                }

                hide();
            }
        });

        bAb3.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.fireSpinAttack(player, CombatScreen.getEnemy());
                } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.multipleAttack(player, CombatScreen.getEnemy());
                } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.waveAttack(player, CombatScreen.getEnemy());
                }

                hide();
            }
        });

        bAb4.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.fireSwordAttack(player, CombatScreen.getEnemy());
                } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.rayAttack(player, CombatScreen.getEnemy());
                } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                    CombatLogic.tearDownAttack(player, CombatScreen.getEnemy());
                }

                hide();
            }
        });

        bReturn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
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
