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

public class GameScreen extends BaseScreen {
    private Player player;

    public GameScreen(final TurnQuest game) {
        super(game);
        this.player = game.getCurrentPlayer();

        game.setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(game.getStage());

        // create the table
        Table table = new Table();
        table.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        table.setFillParent(true);

        // options button
        TextButton bOptions = new TextButton("Options", game.getSkin());
        table.add(bOptions).right();

        // add another column
        table.add();

        // inventory button
        TextButton bInventory = new TextButton("Inventory", game.getSkin());
        table.add(bInventory).left();

        // add another row
        table.row();

        // add another column
        table.add();

        // play button
        TextButton bPlay = new TextButton("Play", game.getSkin());
        table.add(bPlay).center();

        // add another column
        table.add();

        // add another row
        table.row();

        // abilities button
        TextButton bClan = new TextButton("Clan", game.getSkin());
        table.add(bClan).right();

        //return button
        TextButton bReturn = new TextButton("Return", game.getSkin());
        table.add(bReturn).bottom();

        // shop button
        TextButton bShop = new TextButton("Shop", game.getSkin());
        table.add(bShop).left();

        // table padding
        table.padTop(20);
        table.padBottom(20);
        table.padLeft(20);
        table.padRight(20);

        game.getStage().addActor(table);

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
                game.showPreferencesDialog();
            }
        });

        getViewport().apply();
    }

    @Override
    protected void refreshScreen() {
        dispose();
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        //game.getFont().getData().setScale(4); //Changes font size.
        game.getFont().draw(game.getBatch(), "Game Menu", getVirtualWidth() * 0.42f, getVirtualWidth() * 0.77f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleInput();
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
        game.getBackgroundTexture().dispose();
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to return to main menu? \n" +
                "You will have to enter your credentials again.", () -> game.setScreen(new MainMenuScreen(game)), game.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }
}