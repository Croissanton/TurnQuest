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

public class AbilitiesScreen implements Screen {

    final TurnQuest game;

    public AbilitiesScreen (final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        // abilities buttons
        TextButton bAb1 = new TextButton("", getSkin());
        TextButton bAb2 = new TextButton("", getSkin());
        TextButton bAb3 = new TextButton("", getSkin());
        TextButton bAb4 = new TextButton("", getSkin());
        TextButton bAb5 = new TextButton("", getSkin());
        TextButton bAb6 = new TextButton("", getSkin());
        TextButton bAb7 = new TextButton("", getSkin());
        TextButton bAb8 = new TextButton("", getSkin());
        TextButton bAb9 = new TextButton("", getSkin());
        TextButton bAb10 = new TextButton("", getSkin());
        TextButton bAb11 = new TextButton("", getSkin());
        TextButton bAb12 = new TextButton("", getSkin());
        TextButton bAb13 = new TextButton("", getSkin());
        TextButton bAb14 = new TextButton("", getSkin());
        TextButton bAb15 = new TextButton("", getSkin());
        TextButton bAb16 = new TextButton("", getSkin());

        // Abilities table
        Table abilitiesTable = new Table();
        abilitiesTable.setFillParent(true);

        // order the buttons of the table
        abilitiesTable.add(bAb1);
        abilitiesTable.add(bAb2);
        abilitiesTable.add(bAb3);
        abilitiesTable.add(bAb4).row();
        abilitiesTable.add(bAb5);
        abilitiesTable.add(bAb6);
        abilitiesTable.add(bAb7);
        abilitiesTable.add(bAb8).row();
        abilitiesTable.add(bAb9);
        abilitiesTable.add(bAb10);
        abilitiesTable.add(bAb11);
        abilitiesTable.add(bAb12).row();
        abilitiesTable.add(bAb13);
        abilitiesTable.add(bAb14);
        abilitiesTable.add(bAb15);
        abilitiesTable.add(bAb16).row();

        abilitiesTable.padTop(100f); // add some padding at the top

        getStage().addActor(abilitiesTable);

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

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "Abilities", getVirtualWidth()*45/100, getVirtualHeight()*85/100);
        getFont().draw(getBatch(), getCharacterClass(), getVirtualWidth()*45/100, getVirtualHeight()*78/100);
        getBatch().end();

        getStage().act();
        getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    public static void toggleFullscreen(){
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(TurnQuest.getVirtualWidth()/2, TurnQuest.getVirtualHeight()/2);
            getStage().getViewport().update(TurnQuest.getVirtualWidth()/2, TurnQuest.getVirtualHeight()/2, true);
        } else {
            Gdx.graphics.setFullscreenMode(TurnQuest.getDisplayMode());
            getStage().getViewport().update(TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight(), true);
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
