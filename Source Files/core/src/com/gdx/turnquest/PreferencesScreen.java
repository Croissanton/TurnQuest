package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PreferencesScreen implements Screen {

    final TurnQuest game;

    public int VOLUME=30;

    private Texture backgroundTexture;

    OrthographicCamera camera;

    public static final Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();

    public static final int VIRTUAL_WIDTH = dm.width;

    public static final int VIRTUAL_HEIGHT = dm.height;

    public Stage stage;

    public Skin skin;

    Viewport viewport;

    public PreferencesScreen(final TurnQuest game){
        this.game=game;
        backgroundTexture = new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        skin = new Skin(Gdx.files.internal("glassy-ui.json"));

        final CheckBox fullscreen = new CheckBox("Fullscreen",skin);
        final Slider mainVolume = new Slider(0,100,1,false,skin);
        TextButton bBack = new TextButton("Back", skin);

        fullscreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(fullscreen.isChecked()){
                    Gdx.graphics.setFullscreenMode(dm);
                }else {
                    Gdx.graphics.setWindowedMode(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2);
                }
            }
        });

        mainVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                VOLUME=(int) mainVolume.getPercent();

            }
        });
        //I DON'T KNOW HOW TO CHANGE THE BUTTON SIZE :(
        //same bro


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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
