package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.AbilitiesDialog;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.utils.EnemyManager;

import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;

public class CombatScreen extends BaseScreen {
    private final Player player;
    private static Enemy enemy = null;
    private Texture playerTexture;
    private final Texture enemyTexture;
    private final Sprite playerSprite;
    private final Sprite enemySprite;
    private final Label playerHPLabel;
    private final Label playerMPLabel;
    private final Label enemyMPLabel;
    private final Label enemyHPLabel;

    private PlayerManager playerManager;

    private final int maxPlayerHP;
    private final int maxPlayerMP;
    private final int maxEnemyHP;
    private final int maxEnemyMP;
    private final boolean boss;

    ProgressBar playerHPBar;
    ProgressBar playerMPBar;
    ProgressBar enemyHPBar;
    ProgressBar enemyMPBar;

    public CombatScreen(final TurnQuest game, boolean boss) {
        super(game);
        Assets.loadFor(CombatScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));

        this.boss = boss;

        player = game.getCurrentPlayer();
        try {
            playerManager = new PlayerManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (boss) {
            enemy = new EnemyManager().getEnemy("boss1");
        } else {
            enemy = new EnemyManager().getEnemy("enemy1_01");
        }

        maxPlayerHP = player.getHP();
        maxPlayerMP = player.getMP();
        maxEnemyHP = enemy.getHP();
        maxEnemyMP = enemy.getMP();


        game.setStage(new Stage(getViewport()));

        // Load the player and enemy textures
        playerTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/01.png"));
        Texture warriorTexture = new Texture(Gdx.files.internal("Elementals_fire_knight_FREE_v1.1/png/fire_knight/01_idle/idle_1.png"));
        Texture archerTexture = new Texture(Gdx.files.internal("Elementals_Leaf_ranger_Free_v1.0/animations/PNG/1_atk/1_atk_1.png"));
        Texture mageTexture = new Texture(Gdx.files.internal("Elementals_water_priestess_FREE_v1.1/png/01_idle/idle_1.png"));

        if (player.getCharacterClass().equalsIgnoreCase("warrior")) {
            playerTexture = warriorTexture;
        } else if (player.getCharacterClass().equalsIgnoreCase("archer")) {
            playerTexture = archerTexture;
        } else if (player.getCharacterClass().equalsIgnoreCase("mage")) {
            playerTexture = mageTexture;
        }

        if (boss) {
            enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/03.png"));
        } else {
            enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/02.png"));
        }

        // Create the player and enemy sprites
        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition(getVirtualWidth() * 0.18f, getVirtualHeight() * 0.7f); // Set the position of the player sprite
        playerSprite.setScale(6); // Scale the player sprite

        enemySprite = new Sprite(enemyTexture);
        enemySprite.setPosition(getVirtualWidth() * 0.77f, getVirtualHeight() * 0.52f); // Set the position of the enemy sprite
        enemySprite.setScale(4); // Scale the enemy sprite

        TextButton attackButton = new TextButton("Attack", Assets.getSkin());
        TextButton abilitiesButton = new TextButton("Abilities", Assets.getSkin());
        TextButton itemButton = new TextButton("Item", Assets.getSkin());
        TextButton runButton = new TextButton("Run", Assets.getSkin());

        // Create the table
        Table optionsTable = new Table(Assets.getSkin());
        optionsTable.setPosition(TurnQuest.getVirtualWidth() / 2f, 200f, Align.center);
        optionsTable.defaults().space(20f).width(200);
        optionsTable.add(attackButton);
        optionsTable.add(itemButton);
        optionsTable.row();
        optionsTable.add(abilitiesButton);
        optionsTable.add(runButton);

        // Add the table to the stage
        game.getStage().addActor(optionsTable);

        playerHPLabel = new Label("HP: " + maxPlayerHP, Assets.getSkin());
        playerMPLabel = new Label("MP: " + maxPlayerMP, Assets.getSkin());
        enemyHPLabel = new Label("HP: " + maxEnemyHP, Assets.getSkin());
        enemyMPLabel = new Label("MP: " + maxEnemyMP, Assets.getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle progressBarStyleMP = new ProgressBar.ProgressBarStyle();

        progressBarStyleHP.background = Assets.getSkin().getDrawable("progress-bar-health");
        progressBarStyleHP.knobBefore = Assets.getSkin().getDrawable("progress-bar-health-knob");

        progressBarStyleMP.background = Assets.getSkin().getDrawable("progress-bar-mana");
        progressBarStyleMP.knobBefore = Assets.getSkin().getDrawable("progress-bar-mana-knob");

        playerHPBar = new ProgressBar(0, maxPlayerHP, 1, false, progressBarStyleHP);

        playerMPBar = new ProgressBar(0, maxPlayerMP, 1, false, progressBarStyleMP);

        enemyHPBar = new ProgressBar(0,maxEnemyHP, 1, false, progressBarStyleHP);

        enemyMPBar = new ProgressBar(0, maxEnemyMP, 1, false, progressBarStyleMP);


        Table playerTable = new Table(Assets.getSkin());
        playerTable.setPosition(getVirtualWidth() * 0.25f, getVirtualHeight() * 0.25f);
        playerTable.defaults().space(10f);
        playerTable.add(playerHPLabel).row();
        playerTable.add(playerHPBar).width(400f).row();
        playerTable.add(playerMPLabel).row();
        playerTable.add(playerMPBar).width(400f).row();

        game.getStage().addActor(playerTable);

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        Table enemyTable = new Table(Assets.getSkin());
        enemyTable.setPosition(getVirtualWidth() * 0.8f, getVirtualHeight() * 0.25f);
        enemyTable.defaults().space(10f);
        enemyTable.add(enemyHPLabel).row();
        enemyTable.add(enemyHPBar).width(400f).row();
        enemyTable.add(enemyMPLabel).row();
        enemyTable.add(enemyMPBar).width(400f).row();

        game.getStage().addActor(enemyTable);

        //create the table
        Table table = new Table();
        table.defaults().expand();
        table.setFillParent(true);

        game.getStage().addActor(table);

        // apply
        getViewport().apply();

        // Add a listener to the attack button
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CombatLogic.attack(player, enemy);
            }
        });
        // do the same for the other buttons
        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAbilitiesDialog();
            }
        });
        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Fetch inventory
                //Display inventory
                //Select item
                //Use item with CombatLogic.useItem(player, itemID)
            }
        });
        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(CombatLogic.run(player, enemy)){
                    game.pushScreen(new MapScreen(game));
                }
            }
        });
    }

    @Override
    protected void refreshScreen() {
        dispose();
            game.pushScreen(new CombatScreen(game, boss));
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        playerHPLabel.setText("HP: " + player.getHP());
        playerMPLabel.setText("MP: " + player.getMP());
        enemyHPLabel.setText("HP: " + enemy.getHP());
        enemyMPLabel.setText("MP: " + enemy.getMP());

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        playerHPBar.setValue(player.getHP());
        playerMPBar.setValue(player.getMP());
        enemyHPBar.setValue(enemy.getHP());
        enemyMPBar.setValue(enemy.getMP());
        playerSprite.draw(game.getBatch());
        enemySprite.draw(game.getBatch());
        if(player.getHP() <= 0){
            CombatLogic.defeat(player, enemy);
            if(playerManager.savePlayer(player) == 0){
                System.out.println("Player saved");
            }
            else{
                System.out.println("Player not saved");
            }
            game.pushScreen(new MapScreen(game));
        }
        else if(enemy.getHP() <= 0){
            CombatLogic.victory(player, enemy);
            if(playerManager.savePlayer(player) == 0){
                System.out.println("Player saved");
            }
            else{
                System.out.println("Player not saved");
            }
            game.pushScreen(new MapScreen(game));
        }
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleInput();
    }


    @Override
    public void dispose() {
        game.getStage().dispose();
        playerTexture.dispose();
        enemyTexture.dispose();
    }

    private void showAbilitiesDialog() {
        AbilitiesDialog dialog = new AbilitiesDialog("", () -> {
            // Handle login here
        }, Assets.getSkin(), game);
        dialog.show(game.getStage());
    }

    public static Enemy getEnemy() {
        return enemy;
    }
}