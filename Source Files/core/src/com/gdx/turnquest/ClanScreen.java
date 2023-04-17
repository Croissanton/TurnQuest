package com.gdx.turnquest;

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
import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.Player.*;

public class ClanScreen implements Screen {
    final TurnQuest game;

    public ClanScreen(final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        // return button
        TextButton bReturn = new TextButton("Return", getSkin());

        //create the table
        Table table = new Table();
        table.defaults().expand();
        table.setFillParent(true);

        // add return button to the table
        table.add(bReturn).bottom();

        getStage().addActor(table);

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
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "Clan", getVirtualWidth()*48/100, getVirtualHeight()*85/100);
        getFont().draw(getBatch(), getCharacterClass(), getVirtualWidth()*45/100, getVirtualHeight()*75/100);
        getBatch().end();

        getStage().act();
        getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            game.toggleFullscreen();
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
}