package com.gdx.turnquest.screens;

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
import com.gdx.turnquest.dialogs.ConfirmationDialog;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;

import static com.gdx.turnquest.TurnQuest.*;

public class GameScreen implements Screen {
    final TurnQuest game;
    private Player player;

    public GameScreen(final TurnQuest game) {
        this.game = game;
        this.player = getCurrentPlayer();
        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        // create the table
        Table table = new Table();
        table.defaults().expand().size(getVirtualWidth() *.15f, getVirtualHeight() * .1f);
        table.setFillParent(true);

        // options button
        TextButton bOptions = new TextButton("Options", getSkin());
        table.add(bOptions).right();

        // add another column
        table.add();

        // inventory button
        TextButton bInventory = new TextButton("Inventory", getSkin());
        table.add(bInventory).left();

        // add another row
        table.row();

        // add another column
        table.add();

        // play button
        TextButton bPlay = new TextButton("Play", getSkin());
        table.add(bPlay).center();

        // add another column
        table.add();

        // add another row
        table.row();

        // abilities button
        TextButton bClan = new TextButton("Clan", getSkin());
        table.add(bClan).right();

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
                game.setScreen(new CombatScreen(game));
                //game.setScreen(new CharactersScreen(game));
            }
        });

        bInventory.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        bClan.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ClanScreen(game));
            }
        });

        bShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ShopScreen(game));
            }
        });

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

        bOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPreferencesDialog();
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
        getFont().draw(getBatch(), "Game Menu", getVirtualWidth() * 0.42f, getVirtualWidth() * 0.77f);
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