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
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;

import com.gdx.turnquest.logic.CombatLogic;

import static com.gdx.turnquest.TurnQuest.*;

public class CombatScreen implements Screen {
    final TurnQuest game;
    private Player player;
    private Enemy enemy;
    private final Texture playerTexture;
    private final Texture enemyTexture;
    private final Sprite playerSprite;
    private final Sprite enemySprite;
    private Label playerHpLabel;
    private Label playerMpLabel;
    private Label enemyMpLabel;
    private Label enemyHpLabel;

    public CombatScreen(final TurnQuest game) {
        this.game = game;
        player = game.getCurrentPlayer();

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        game.setStage(new Stage(getViewport()));

        // Load the player and enemy textures
        playerTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/01.png"));
        enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/09.png"));

        // Create the player and enemy sprites
        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition(getVirtualWidth()*2/8f, getVirtualHeight()/2f); // Set the position of the player sprite
        playerSprite.setScale(2); // Scale the player sprite

        enemySprite = new Sprite(enemyTexture);
        enemySprite.setPosition(getVirtualWidth()*6/8f, getVirtualHeight()/2f); // Set the position of the enemy sprite
        enemySprite.setScale(2); // Scale the enemy sprite

        TextButton attackButton = new TextButton("Attack", game.getSkin());
        TextButton magicButton = new TextButton("Magic", game.getSkin());
        TextButton itemButton = new TextButton("Item", game.getSkin());
        TextButton runButton = new TextButton("Run", game.getSkin());

        // Create the table
        Table optionsTable = new Table(game.getSkin());
        optionsTable.setPosition(TurnQuest.getVirtualWidth() / 2f, 200f, Align.center);
        optionsTable.defaults().space(20f);
        optionsTable.add(attackButton);
        optionsTable.add(itemButton);
        optionsTable.row();
        optionsTable.add(magicButton);
        optionsTable.add(runButton);

        // Add the table to the stage
        game.getStage().addActor(optionsTable);

        playerHpLabel = new Label("HP: " + player.getHP(), game.getSkin());
        playerMpLabel = new Label("MP: " + player.getMP(), game.getSkin());
        enemyHpLabel = new Label("HP: " + 0, game.getSkin());
        enemyMpLabel = new Label("MP: " + 0, game.getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle progressBarStyleMP = new ProgressBar.ProgressBarStyle();

        progressBarStyleHP.background = game.getSkin().getDrawable("progress-bar-health");
        progressBarStyleHP.knobBefore = game.getSkin().getDrawable("progress-bar-health-knob");

        progressBarStyleMP.background = game.getSkin().getDrawable("progress-bar-mana");
        progressBarStyleMP.knobBefore = game.getSkin().getDrawable("progress-bar-mana-knob");

        ProgressBar playerHpBar = new ProgressBar(0, player.getHP(), 1, false, progressBarStyleHP);
        playerHpBar.setValue(player.getHP()); // Set the initial value of the bar to getHP() (full)

        ProgressBar playerMpBar = new ProgressBar(0, player.getMP(), 1, false, progressBarStyleMP);
        playerMpBar.setValue(player.getMP()); // Set the initial value of the bar to getHP() (full)

        ProgressBar enemyHpBar = new ProgressBar(0,player.getHP(), 1, false, progressBarStyleHP); //TODO: change this to enemy when implemented
        enemyHpBar.setValue(player.getHP()); // Set the initial value of the bar to getHP() (full)

        ProgressBar enemyMpBar = new ProgressBar(0, player.getMP(), 1, false, progressBarStyleMP); //TODO: change this to enemy when implemented
        enemyMpBar.setValue(player.getMP()); // Set the initial value of the bar to getHP() (full)


        Table playerTable = new Table(game.getSkin());
        playerTable.setPosition(getVirtualWidth() * 0.25f, getVirtualHeight() * 0.25f);
        playerTable.defaults().space(10f);
        playerTable.add(playerHpLabel).row();
        playerTable.add(playerHpBar).width(400f).row();
        playerTable.add(playerMpLabel).row();
        playerTable.add(playerMpBar).width(400f).row();

        game.getStage().addActor(playerTable);

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        Table enemyTable = new Table(game.getSkin());
        enemyTable.setPosition(getVirtualWidth() * 0.8f, getVirtualHeight() * 0.25f);
        enemyTable.defaults().space(10f);
        enemyTable.add(enemyHpLabel).row();
        enemyTable.add(enemyHpBar).width(400f).row();
        enemyTable.add(enemyMpLabel).row();
        enemyTable.add(enemyMpBar).width(400f).row();

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
        magicButton.addListener(new ClickListener() {
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

        playerHpLabel.setText("HP: " + player.getHP());
        playerMpLabel.setText("MP: " + player.getMP());
        enemyHpLabel.setText("HP: " + 0);
        enemyMpLabel.setText("MP: " + 0);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
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