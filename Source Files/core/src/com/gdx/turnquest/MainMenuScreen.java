package com.gdx.turnquest;

import com.gdx.turnquest.TurnQuest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.turnquest.TurnQuest;

public class MainMenuScreen implements Screen {


    final TurnQuest game;
    private final Texture backgroundTexture;

    OrthographicCamera camera;

    public static final Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();

    public static final int VIRTUAL_WIDTH = dm.width;

    public static final int VIRTUAL_HEIGHT = dm.height;

    public static Stage stage;

    public Skin skin;

    Viewport viewport;



    public MainMenuScreen(final TurnQuest game) {


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

        TextButton bStart = new TextButton("Start", skin);
        TextButton bOptions = new TextButton("Options", skin);
        TextButton bQuit = new TextButton("Quit", skin);
        //I DON'T KNOW HOW TO CHANGE THE BUTTON SIZE :(

        bStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLoginDialog();
            }
        });
        bOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPreferencesDialog();
            }
        });

        bQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

        bQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLoginDialog();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bStart).center().padBottom(50f).row();
        table.add(bOptions).center().padBottom(50f).row();
        table.add(bQuit).center().padBottom(50f);

        table.padTop(100f); // add some padding at the top

        stage.addActor(table);


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
        game.font.draw(game.batch, "Welcome to TurnQuest!", VIRTUAL_WIDTH*35/100, VIRTUAL_HEIGHT*85/100);
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
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to quit?", new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        }, skin);
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(stage);
    }

    private void showPreferencesDialog() {
        PreferencesDialog dialog = new PreferencesDialog("Options", "", new Runnable() {
            @Override
            public void run() {
                
            }
        }, skin);
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(stage);
    }
    private void showLoginDialog() {
        LoginDialog dialog = new LoginDialog("Login", new Runnable() {
            @Override
            public void run() {
                // Handle login here
            }
        }, skin, game);
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(stage);
    }
}
