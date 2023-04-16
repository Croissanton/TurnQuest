package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import static com.gdx.turnquest.TurnQuest.*;

public class GameScreen implements Screen {
    final TurnQuest game;

    final Player player;

    public GameScreen(final TurnQuest game, final Player player) {
        this.game = game;
        this.player = player;

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        // create the table
        Table table = new Table();
        table.defaults().expand().size(getVirtualWidth() * 15 / 100, getVirtualHeight() * 10 / 100);
        table.setFillParent(true);

        // play button
        TextButton bPlay = new TextButton("Play", getSkin());
        table.add(bPlay).right();

        // inventory button
        TextButton bInventory = new TextButton("Inventory", getSkin());
        table.add();
        table.add(bInventory).left();

        // add another row
        table.row();

        // abilities button
        TextButton bAbilities = new TextButton("Abilities", getSkin());
        table.add(bAbilities).right();

        //return button
        TextButton bReturn = new TextButton("Return", getSkin());
        table.add(bReturn).bottom();

        // shop button
        TextButton bShop = new TextButton("Shop", getSkin());
        table.add(bShop).left();

        // table padding
        table.padTop(20);
        table.padBottom(20);
        table.padLeft(20);
        table.padRight(20);

        getStage().addActor(table);

        bPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        bInventory.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game, player));
            }
        });

        bAbilities.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AbilitiesScreen(game, player));
            }
        });

        bShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ShopScreen(game, player));
            }
        });

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

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
        getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "Game Menu", getVirtualWidth() * 42 / 100, getVirtualWidth() * 77 / 100);
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
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to return to main menu? \n" +
                "You will have to enter your credentials again.", new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
            }
        }, getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(getStage());
    }
}