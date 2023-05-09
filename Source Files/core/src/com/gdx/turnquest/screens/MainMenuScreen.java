package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.dialogs.ConfirmationDialog;
import com.gdx.turnquest.dialogs.GameSelectionDialog;
import static com.gdx.turnquest.TurnQuest.*;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(final TurnQuest game) {
        super(game);

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));
        game.setStage(new Stage(getViewport()));


        TextButton bStart = new TextButton("Start", game.getSkin());
        TextButton bOptions = new TextButton("Options", game.getSkin());
        TextButton bQuit = new TextButton("Quit", game.getSkin());

        //I DON'T KNOW HOW TO CHANGE THE BUTTON SIZE :(

        bStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showGameSelectionDialog();
            }
        });
        bOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showPreferencesDialog();
            }
        });
        bQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bStart).center().padBottom(50f).row();
        table.add(bOptions).center().padBottom(50f).row();
        table.add(bQuit).center().padBottom(50f);

        table.padTop(100f); // add some padding at the top

        game.getStage().addActor(table);

        getViewport().apply();
    }

    @Override
    protected void refreshScreen() {
        dispose();
        game.setScreen(new MainMenuScreen(game));
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getFont().getData().setScale(4); //Changes font size.
        game.getFont().draw(game.getBatch(), "Welcome to TurnQuest!", getVirtualWidth()*0.35f, getVirtualHeight()*0.85f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleInput();
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to quit?", () -> Gdx.app.exit(), game.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }

    private void showGameSelectionDialog() {
        GameSelectionDialog dialog = new GameSelectionDialog("Game Selection", "Do you want to create a new character?", () -> {
            // Handle login here
        }, game.getSkin(), game);
        dialog.show(game.getStage());
    }
}
