package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.utils.EnemyManager;
import com.gdx.turnquest.utils.ItemManager;

import com.gdx.turnquest.logic.CombatLogic;

import static com.gdx.turnquest.TurnQuest.*;

public class CombatScreen implements Screen {
    final TurnQuest game;
    private final Player player;
    private final Enemy enemy;
    private Texture playerTexture;
    private final Texture enemyTexture;
    private final Sprite playerSprite;
    private final Sprite enemySprite;
    private final Label playerHPLabel;
    private final Label playerMPLabel;
    private final Label enemyMPLabel;
    private final Label enemyHPLabel;

    private int maxPlayerHP;
    private int maxPlayerMP;
    private int maxEnemyHP;
    private int maxEnemyMP;

    ProgressBar playerHPBar;
    ProgressBar playerMPBar;
    ProgressBar enemyHPBar;
    ProgressBar enemyMPBar;

    public CombatScreen(final TurnQuest game) {
        this.game = game;
        player = game.getCurrentPlayer();
        enemy = new EnemyManager().getEnemy("enemy1_01");
        maxPlayerHP = player.getHP();
        maxPlayerMP = player.getMP();
        maxEnemyHP = enemy.getHP();
        maxEnemyMP = enemy.getMP();

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

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

        enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/09.png"));

        // Create the player and enemy sprites
        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition(getVirtualWidth() * 0.18f, getVirtualHeight() * 0.7f); // Set the position of the player sprite
        playerSprite.setScale(6); // Scale the player sprite

        enemySprite = new Sprite(enemyTexture);
        enemySprite.setPosition(getVirtualWidth() * 0.75f, getVirtualHeight() * 0.48f); // Set the position of the enemy sprite
        enemySprite.setScale(2); // Scale the enemy sprite

        TextButton attackButton = new TextButton("Attack", game.getSkin());
        TextButton abtlitiesButton = new TextButton("Abilities", game.getSkin());
        TextButton itemButton = new TextButton("Item", game.getSkin());
        TextButton runButton = new TextButton("Run", game.getSkin());

        // Create the table
        Table optionsTable = new Table(game.getSkin());
        optionsTable.setPosition(TurnQuest.getVirtualWidth() / 2f, 200f, Align.center);
        optionsTable.defaults().space(20f);
        optionsTable.add(attackButton);
        optionsTable.add(itemButton);
        optionsTable.row();
        optionsTable.add(abtlitiesButton);
        optionsTable.add(runButton);

        // Add the table to the stage
        game.getStage().addActor(optionsTable);

        playerHPLabel = new Label("HP: " + maxPlayerHP, game.getSkin());
        playerMPLabel = new Label("MP: " + maxPlayerMP, game.getSkin());
        enemyHPLabel = new Label("HP: " + maxEnemyHP, game.getSkin());
        enemyMPLabel = new Label("MP: " + maxEnemyMP, game.getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle progressBarStyleMP = new ProgressBar.ProgressBarStyle();

        progressBarStyleHP.background = game.getSkin().getDrawable("progress-bar-health");
        progressBarStyleHP.knobBefore = game.getSkin().getDrawable("progress-bar-health-knob");

        progressBarStyleMP.background = game.getSkin().getDrawable("progress-bar-mana");
        progressBarStyleMP.knobBefore = game.getSkin().getDrawable("progress-bar-mana-knob");

        playerHPBar = new ProgressBar(0, maxPlayerHP, 1, false, progressBarStyleHP);

        playerMPBar = new ProgressBar(0, maxPlayerMP, 1, false, progressBarStyleMP);

        enemyHPBar = new ProgressBar(0,maxEnemyHP, 1, false, progressBarStyleHP);

        enemyMPBar = new ProgressBar(0, maxEnemyMP, 1, false, progressBarStyleMP);


        Table playerTable = new Table(game.getSkin());
        playerTable.setPosition(getVirtualWidth() * 0.25f, getVirtualHeight() * 0.25f);
        playerTable.defaults().space(10f);
        playerTable.add(playerHPLabel).row();
        playerTable.add(playerHPBar).width(400f).row();
        playerTable.add(playerMPLabel).row();
        playerTable.add(playerMPBar).width(400f).row();

        game.getStage().addActor(playerTable);

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        Table enemyTable = new Table(game.getSkin());
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
                System.out.println(enemy.getHP());
            }
        });
        // do the same for the other buttons
        abtlitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CombatLogic.magicAttack(player, enemy);
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
                    //game.setScreen(new OverworldScreen(game));
                }
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.getStage());
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
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        playerHPBar.setValue(player.getHP());
        playerMPBar.setValue(player.getMP());
        enemyHPBar.setValue(enemy.getHP());
        enemyMPBar.setValue(enemy.getMP());
        playerSprite.draw(game.getBatch());
        enemySprite.draw(game.getBatch());
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            dispose();
            game.setScreen(new CombatScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.getStage().dispose();
        playerTexture.dispose();
        enemyTexture.dispose();
    }
}