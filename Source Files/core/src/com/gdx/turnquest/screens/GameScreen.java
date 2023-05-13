package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.ConfirmationDialog;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;

import static com.gdx.turnquest.TurnQuest.*;

public class GameScreen extends BaseScreen {
    private Player player;

    public GameScreen(final TurnQuest game) {
        super(game);
        this.player = game.getCurrentPlayer();
    }

    @Override
    public void show() {
        Assets.loadFor(GameScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        game.setStage(new Stage(getViewport()));
        game.getStage().addActor(createUIComponents());
        getViewport().apply();
        super.show();
    }

    public Table createUIComponents() {
        // create the table
        Table table = new Table();
        table.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        table.setFillParent(true);

        // options button
        TextButton bOptions = new TextButton("Options", Assets.getSkin());
        table.add(bOptions).right();

        // add another column
        table.add();

        // inventory button
        TextButton bInventory = new TextButton("Inventory", Assets.getSkin());
        table.add(bInventory).left();

        // add another row
        table.row();

        // add another column
        table.add();

        // play button
        TextButton bPlay = new TextButton("Play", Assets.getSkin());
        table.add(bPlay).center();

        // add another column
        table.add();

        // add another row
        table.row();

        // abilities button
        TextButton bClan = new TextButton("Clan", Assets.getSkin());
        table.add(bClan).right();

        //return button
        TextButton bReturn = new TextButton("Return", Assets.getSkin());
        table.add(bReturn).bottom();

        // shop button
        TextButton bShop = new TextButton("Shop", Assets.getSkin());
        table.add(bShop).left();

        // table padding
        table.padTop(20);
        table.padBottom(20);
        table.padLeft(20);
        table.padRight(20);

        bPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new MapScreen(game));
                //game.pushScreen(new CharactersScreen(game));
            }
        });

        bInventory.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new InventoryScreen(game));
            }
        });

        bClan.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new ClanScreen(game));
            }
        });

        bShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new ShopScreen(game));
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

        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());
        Assets.getFont().draw(game.getBatch(), "Game Menu", getVirtualWidth() * 0.42f, getVirtualWidth() * 0.77f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to return to main menu? \n" +
                "You will have to enter your credentials again.", () -> game.popScreen(), Assets.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }
}