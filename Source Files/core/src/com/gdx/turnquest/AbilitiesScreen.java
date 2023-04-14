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

public class AbilitiesScreen implements Screen {
    final TurnQuest game;
    public AbilitiesScreen(final TurnQuest game) {
        this.game = game;
        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        TextButton bReturn = new TextButton("Return", getSkin());

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bReturn).center().padBottom(50f).row();

        table.padTop(100f); // add some padding at the top

        getStage().addActor(table);

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
        getFont().draw(getBatch(), "Abilities", getVirtualWidth()*45/100, getVirtualHeight()*85/100);
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