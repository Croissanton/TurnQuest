package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

public class MainMenuScreen implements Screen {


    final TurnQuest game;
    private Texture backgroundTexture;

    OrthographicCamera camera;

    public static final Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();

    public static final int VIRTUAL_WIDTH = dm.width;

    public static final int VIRTUAL_HEIGHT = dm.height;

    public Stage stage;

    Viewport viewport;



    public MainMenuScreen(final TurnQuest game) {


        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        //ATTEMPT OF CREATING A MAIN MENU WITH A TABLE W/ BUTTONS (failed)
        //does not show but it causes no errors, suspected bad position in code


        Skin skin = new Skin(Gdx.files.internal("glassy-ui.json"));

        TextButton button = new TextButton("Click me!", skin);
        button.setSize(1000, 500); // set the size of the button to 100 pixels wide and 50 pixels tall
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button click here
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(button).center().padBottom(50f);

        stage.addActor(table);

        viewport = new ScreenViewport();

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
        game.font.getData().setScale(2); //Changes font size.
        game.font.draw(game.batch, "Welcome to TurnQuest! ", 100, 400);
        game.font.draw(game.batch, "Click anywhere to begin! ", 100, 350);
        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(dm.width / 2, dm.height / 2);
            } else {
                Gdx.graphics.setFullscreenMode(dm);
            }
        }
        game.batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    }
}
