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

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.entities.Player.*;

public class CombatScreen implements Screen {
    final TurnQuest game;
    private static Texture playerTexture;
    private static Texture enemyTexture;
    private static Sprite playerSprite;
    private static Sprite enemySprite;

    private static Label playerHpLabel;
    private static Label playerMpLabel;
    private static Label enemyMpLabel;
    private static Label enemyHpLabel;

    public CombatScreen(final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));

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

        TextButton attackButton = new TextButton("Attack", getSkin());
        TextButton magicButton = new TextButton("Magic", getSkin());
        TextButton itemButton = new TextButton("Item", getSkin());
        TextButton runButton = new TextButton("Run", getSkin());

        // Create the table
        Table optionsTable = new Table(getSkin());
        optionsTable.setPosition(TurnQuest.getVirtualWidth() / 2f, 200f, Align.center);
        optionsTable.defaults().space(20f);
        optionsTable.add(attackButton);
        optionsTable.add(itemButton);
        optionsTable.row();
        optionsTable.add(magicButton);
        optionsTable.add(runButton);

        playerHpLabel = new Label("HP: " + getHP(), getSkin());
        playerMpLabel = new Label("MP: " + getMP(), getSkin());
        enemyHpLabel = new Label("HP: " + 0, getSkin());
        enemyMpLabel = new Label("MP: " + 0, getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle progressBarStyleMP = new ProgressBar.ProgressBarStyle();

        progressBarStyleHP.background = getSkin().getDrawable("progress-bar-health");
        progressBarStyleHP.knobBefore = getSkin().getDrawable("progress-bar-health-knob");

        progressBarStyleMP.background = getSkin().getDrawable("progress-bar-mana");
        progressBarStyleMP.knobBefore = getSkin().getDrawable("progress-bar-mana-knob");

        ProgressBar hpBar = new ProgressBar(0, getHP(), 1, false, progressBarStyleHP);
        hpBar.setValue(getHP()); // Set the initial value of the bar to getHP() (full)

        ProgressBar mpBar = new ProgressBar(0, getMP(), 1, false, progressBarStyleMP);
        hpBar.setValue(getMP()); // Set the initial value of the bar to getHP() (full)


        Table statusTable = new Table(getSkin());
        statusTable.setPosition(315f, TurnQuest.getVirtualHeight()/10f);
        statusTable.defaults().space(10f);
        statusTable.add(playerHpLabel).row();
        statusTable.add(hpBar).width(400f).row();
        statusTable.add(playerMpLabel).row();
        statusTable.add(mpBar).width(400f).row();

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        statusTable.add(enemyHpLabel).right().row();
        statusTable.add(enemyMpLabel).right();
        statusTable.pack();

        getStage().addActor(statusTable);



        // Add the table to the stage
        getStage().addActor(optionsTable);

        //create the table
        Table table = new Table();
        table.defaults().expand();
        table.setFillParent(true);

        getStage().addActor(table);

        // apply
        getViewport().apply();

        // Add a listener to the attack button
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Attack button clicked");
            }
        });
        // do the same for the other buttons
        magicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Magic button clicked");
            }
        });
        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Item button clicked");
            }
        });
        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Run button clicked");
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        playerHpLabel.setText("HP: " + getHP());
        playerMpLabel.setText("MP: " + getMP());
        enemyHpLabel.setText("HP: " + 0);
        enemyMpLabel.setText("MP: " + 0);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        playerSprite.draw(getBatch());
        enemySprite.draw(getBatch());
        getBatch().end();

        getStage().act();
        getStage().draw();

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
        getStage().dispose();
        getBackgroundTexture().dispose();
        playerTexture.dispose();
        enemyTexture.dispose();
    }
}