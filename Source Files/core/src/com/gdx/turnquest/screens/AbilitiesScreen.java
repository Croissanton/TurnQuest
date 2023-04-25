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

public class AbilitiesScreen implements Screen {

    final TurnQuest game;

    public AbilitiesScreen (final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));

        // abilities buttons
        TextButton bAb1 = new TextButton("Ab1", getSkin());
        TextButton bAb2 = new TextButton("Ab2", getSkin());
        TextButton bAb3 = new TextButton("Ab3", getSkin());
        TextButton bAb4 = new TextButton("Ab4", getSkin());
        TextButton bAb5 = new TextButton("Ab5", getSkin());
        TextButton bAb6 = new TextButton("Ab6", getSkin());

        // set color to locked
        bAb1.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb2.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb3.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb4.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb5.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb6.setColor(0.3f, 0.7f, 0.8f, 0.5f);

        // Abilities table
        Table abilitiesTable = new Table();
        abilitiesTable.defaults().size(getVirtualWidth() * .12f, getVirtualHeight() * .1f).pad(20);
        abilitiesTable.setFillParent(true);

        // order the buttons of the table
        abilitiesTable.add(bAb1);
        abilitiesTable.add(bAb2).row();
        abilitiesTable.add(bAb3);
        abilitiesTable.add(bAb4).row();
        abilitiesTable.add(bAb5);
        abilitiesTable.add(bAb6).row();

        abilitiesTable.padTop(100f); // add some padding at the top

        getStage().addActor(abilitiesTable);

        bAb1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb1.setColor(0.3f, 0.7f, 0.8f, 1);
            }
        });

        bAb2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb2.setColor(0.3f, 0.7f, 0.8f, 1);
            }
        });

        bAb3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb3.setColor(0.3f, 0.7f, 0.8f, 1);
            }
        });

        bAb4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb4.setColor(0.3f, 0.7f, 0.8f, 1);
            }
        });

        bAb5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb5.setColor(0.3f, 0.7f, 0.8f, 1);
            }
        });

        bAb6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb6.setColor(0.3f, 0.7f, 0.8f, 1);
            }
        });

        // table buttons
        TextButton bReturn = new TextButton("Return", getSkin());
        TextButton bLeftArrow = new TextButton("<-", getSkin());
        TextButton bRightArrow = new TextButton("->", getSkin());

        // table for return
        Table table = new Table();
        // add some padding and expand each cell
        table.defaults().expand().pad(50);
        table.setFillParent(true);

        // order the buttons of the table
        table.add(bLeftArrow).left();
        table.add();
        table.add(bRightArrow).right();
        table.row();
        table.add();
        table.add(bReturn).bottom();

        getStage().addActor(table);

        // if an arrow is clicked, go to abilities screen
        bRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        bLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        getViewport().apply();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "Abilities", getVirtualWidth()*.45f, getVirtualHeight()*.85f);
        getFont().draw(getBatch(), getCurrentPlayer().getCharacterClass(), getVirtualWidth()*.45f, getVirtualHeight()*.78f);
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
}
