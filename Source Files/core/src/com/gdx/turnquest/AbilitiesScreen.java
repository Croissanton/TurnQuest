package com.gdx.turnquest;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
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

public class AbilitiesScreen implements Screen {
    final TurnQuest game;

    private final Texture backgroundTexture;

    OrthographicCamera camera;

    public static Stage stage;

    public Skin skin;

    Viewport viewport;
    public AbilitiesScreen(final TurnQuest game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());

        viewport = new FitViewport(TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight(), camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<Skin>("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
        game.getManager().load(skinAssetDescriptor);
        game.getManager().finishLoading();

        skin = game.getManager().get(skinAssetDescriptor);

        TextButton bReturn = new TextButton("Return", skin);

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bReturn).center().padBottom(50f).row();

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
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        game.getFont().getData().setScale(4); //Changes font size.
        game.getFont().draw(game.getBatch(), "Abilities", TurnQuest.getVirtualWidth()*45/100, TurnQuest.getVirtualHeight()*85/100);
        game.getBatch().end();

        stage.act();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    public static void toggleFullscreen(){
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(TurnQuest.getVirtualWidth()/2, TurnQuest.getVirtualHeight()/2);
            stage.getViewport().update(TurnQuest.getVirtualWidth()/2, TurnQuest.getVirtualHeight()/2, true);
        } else {
            Gdx.graphics.setFullscreenMode(TurnQuest.getDisplayMode());
            stage.getViewport().update(TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight(), true);
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
}