package com.gdx.turnquest.screens;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.dialogs.ConfirmationDialog;
import com.gdx.turnquest.dialogs.GameSelectionDialog;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.TurnQuest.getVirtualWidth;

public class MainMenuScreen implements Screen {


    private final TurnQuest game;


    public MainMenuScreen(final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());


        TextButton bStart = new TextButton("Start", getSkin());
        TextButton bOptions = new TextButton("Options", getSkin());
        TextButton bQuit = new TextButton("Quit", getSkin());
        //I DON'T KNOW HOW TO CHANGE THE BUTTON SIZE :(

        bStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showGameSelectionDialog();
            }
        });
        bOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPreferencesDialog();
            }
        });

        bQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

        bQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bStart).center().padBottom(50f).row();
        table.add(bOptions).center().padBottom(50f).row();
        table.add(bQuit).center().padBottom(50f);

        table.padTop(100f); // add some padding at the top

        getStage().addActor(table);

        getViewport().apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "Welcome to TurnQuest!", getVirtualWidth()*35/100, getVirtualHeight()*85/100);
        getBatch().end();

        getStage().act();
        getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();

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
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to quit?", new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        }, getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(getStage());
    }

    private void showGameSelectionDialog() {
        GameSelectionDialog dialog = new GameSelectionDialog("Game Selection", "Do you want to create a new character?", new Runnable() {
            @Override
            public void run() {
                // Handle login here
            }
        }, getSkin(), game);
        dialog.show(getStage());
    }
}
