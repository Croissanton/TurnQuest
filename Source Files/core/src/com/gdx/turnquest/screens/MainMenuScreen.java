package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.ConfirmationDialog;
import com.gdx.turnquest.dialogs.GameSelectionDialog;
import static com.gdx.turnquest.TurnQuest.*;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(final TurnQuest game) {
        super(game);
        Assets.loadFor(MainMenuScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));
        game.setStage(new Stage(getViewport()));


        TextButton bStart = new TextButton("Start", Assets.getSkin());
        TextButton bOptions = new TextButton("Options", Assets.getSkin());
        TextButton bQuit = new TextButton("Quit", Assets.getSkin());

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
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());

        GlyphLayout layout = new GlyphLayout();
        layout.setText(Assets.getTitleFont(), "Welcome to TurnQuest!");
        Assets.getFont().getData().setScale(1.5f); //Changes font size.
        Assets.getFont().draw(game.getBatch(), layout, getVirtualWidth()*0.5f - layout.width/2, getVirtualHeight()*0.85f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleInput();
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to quit?", () -> Gdx.app.exit(), Assets.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }

    private void showGameSelectionDialog() {
        GameSelectionDialog dialog = new GameSelectionDialog("Game Selection", "Do you want to create a new character?", () -> {
            // Handle login here
        }, Assets.getSkin(), game);
        dialog.show(game.getStage());
    }
}
