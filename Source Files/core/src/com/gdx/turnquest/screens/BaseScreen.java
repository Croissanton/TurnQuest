package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.TurnQuest.getVirtualHeight;

public abstract class BaseScreen implements Screen {

    protected final TurnQuest game;
    protected Stage stage;

    public BaseScreen(final TurnQuest game) {
        this.game = game;
        this.stage = game.getStage();
        Assets.load();
    }

    protected abstract void refreshScreen();

    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            refreshScreen();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.getStage());
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        TurnQuest.getViewport().update(width, height, true);
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
    }
}
