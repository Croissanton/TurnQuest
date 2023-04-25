package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.entities.Player.*;

public class ClanScreen implements Screen {
    final TurnQuest game;

    public ClanScreen(final TurnQuest game) {
        this.game = game;

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        game.setStage(new Stage(getViewport()));

        // return button
        TextButton bReturn = new TextButton("Return", game.getSkin());

        //create the table
        Table table = new Table();
        table.defaults().expand();
        table.setFillParent(true);

        // add return button to the table
        table.add(bReturn).bottom();

        game.getStage().addActor(table);

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        // apply
        getViewport().apply();
    }

    @Override
    public void show() {Gdx.input.setInputProcessor(game.getStage());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        //game.getFont().getData().setScale(4); //Changes font size.
        game.getFont().draw(game.getBatch(), "Clan", getVirtualWidth()*.48f, getVirtualHeight()*.85f);
        game.getFont().draw(game.getBatch(), getCharacterClass(), getVirtualWidth()*0.45f, getVirtualHeight()*.75f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

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
        game.getStage().dispose();
    }
}