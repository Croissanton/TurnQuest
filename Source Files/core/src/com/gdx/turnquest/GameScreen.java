package com.gdx.turnquest;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.gdx.turnquest.MainMenuScreen.dm;

public class GameScreen implements Screen {
    final TurnQuest game;

    private final Texture backgroundTexture;

    OrthographicCamera camera;

    public static final int VIRTUAL_WIDTH = dm.width;

    public static final int VIRTUAL_HEIGHT = dm.height;

    public static Stage stage;

    public static float bHeight = VIRTUAL_HEIGHT * 10 / 100;
    public static float bWidth = VIRTUAL_WIDTH * 15 / 100;

    public Skin skin;

    Viewport viewport;
    public GameScreen(final TurnQuest game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<Skin>("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
        game.manager.load(skinAssetDescriptor);
        game.manager.finishLoading();

        skin = game.manager.get(skinAssetDescriptor);

        // create the table
        Table table = new Table();
        table.defaults().expand().size(bWidth, bHeight);
        table.setFillParent(true);

        // play button
        TextButton bPlay = new TextButton("Play", skin);
        table.add(bPlay);

        // inventory button
        TextButton bInventory = new TextButton("Inventory", skin);
        table.add();
        table.add(bInventory);

        // add another row
        table.row();

        // abilities button
        TextButton bAbilities = new TextButton("Abilities", skin);
        table.add(bAbilities);

        //return button
        TextButton bReturn = new TextButton("Return", skin);
        table.add(bReturn);

        // shop button
        TextButton bShop = new TextButton("Shop", skin);
        table.add(bShop);

        // table padding
        table.padTop(20);
        table.padBottom(20);
        table.padLeft(20);
        table.padRight(20);

        stage.addActor(table);

        bPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        bInventory.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        bAbilities.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AbilitiesScreen(game));
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

        viewport.apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        game.font.getData().setScale(4); //Changes font size.
        game.font.draw(game.batch, "Game Menu", VIRTUAL_WIDTH * 42 / 100, VIRTUAL_HEIGHT * 77 / 100);
        game.batch.end();

        stage.act();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    public static void toggleFullscreen(){
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2);
            stage.getViewport().update(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, true);
        } else {
            Gdx.graphics.setFullscreenMode(dm);
            stage.getViewport().update(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to return to main menu? \n" +
                "You will have to enter your credentials again.", new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
            }
        }, skin);
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(stage);
    }
}