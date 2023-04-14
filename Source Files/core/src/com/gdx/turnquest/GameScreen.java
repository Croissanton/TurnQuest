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

public class GameScreen implements Screen {
    final TurnQuest game;

    private final Texture backgroundTexture;

    OrthographicCamera camera;

    public static Stage stage;

    private static float bHeight = TurnQuest.getVirtualHeight() * 10 / 100;
    private static float bWidth = TurnQuest.getVirtualWidth() * 15 / 100;

    public Skin skin;

    Viewport viewport;
    public GameScreen(final TurnQuest game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("skies/Free DEMO Pixel Skies Background pack by Digital Moons/Pixel Skies 1920x1080px (Full HD)/demo03_PixelSky_1920x1080.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());

        viewport = new FitViewport(TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight(), camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<Skin>("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
        game.getManager().load(skinAssetDescriptor);
        game.getManager().finishLoading();

        skin = game.getManager().get(skinAssetDescriptor);

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
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        game.getFont().getData().setScale(4); //Changes font size.
        game.getFont().draw(game.getBatch(), "Game Menu", TurnQuest.getVirtualWidth() * 42 / 100, TurnQuest.getVirtualWidth() * 77 / 100);
        game.getBatch().end();

        stage.act();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            //toggleFullscreen();
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