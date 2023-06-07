package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;

import static com.gdx.turnquest.TurnQuest.*;

/**
 * This class serves as a base for all screens.
 * It contains the basic methods that all screens should have.
 *
 * @author Ignacy
 */

public abstract class BaseScreen implements Screen {

    protected final TurnQuest game;
    protected Stage stage;

    public BaseScreen(final TurnQuest game) {
        this.game = game;
        this.stage = game.getStage();
    }

    public void refreshScreen() {
        game.getStage().clear();
        game.getStage().addActor(createUIComponents());
    }

    public abstract Table createUIComponents();

    protected void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            refreshScreen();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if(getGeneralVolume() == 0){
                game.setGeneralVolume(50);
            }else {
                game.setGeneralVolume(0);
            }
        }

    }

    protected TextButton tutorialButton (String tutorial) {
        //add a tutorial button to the top left of the screen
        TextButton tutorialButton = new TextButton("?", Assets.getSkin());
        int padding = 20;
        tutorialButton.setPosition(padding, getVirtualHeight() - tutorialButton.getHeight()-padding);
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showTutorialDialog(tutorial);
            }
        });
        return tutorialButton;
    }


    /**
     * Called when a screen becomes the current screen for a game.
     * This is where you should initialize resources and set up your screen's UI or game objects.
     * This applies for all show() functions of all screens.
     */

    @Override
    public void show() {
        Assets.load();
        Gdx.input.setInputProcessor(game.getStage());
    }

    /**
     *  Called every frame, usually 60 times per second. This is where you should update your game logic, handle input, and draw graphics.
     * @param delta The time in seconds since the last render.
     */

    @Override
    public void render(float delta) {

    }

    /**
     *  Called when the window is resized. You should update your camera and viewport here to ensure that your game scales correctly on different screen sizes.
     */

    @Override
    public void resize(int width, int height) {
        TurnQuest.getViewport().update(width, height, true);
    }

    /**
     * Called when the application is paused. An application is paused before it is destroyed, when a user pressed the Home button on Android or an incoming call happened.
     * On the desktop this will only be called immediately before dispose() is called.
     */

    @Override
    public void pause() {

    }

    /**
     * Called when the application is resumed from a paused state, usually when it regains focus.
     * On Android this corresponds to an Activity.onResume() call. On desktop this method will never be called.
     */

    @Override
    public void resume() {

    }

    /**
     * Called when the current screen is replaced by another one.
     * This typically happens when you switch to a different screen in your game.
     * Should clean up any resources that are no longer needed.
     */

    @Override
    public void hide() {

    }

    /**
     * Called when the screen should release all its resources.
     * This is typically called when the application is closing or when the resources associated with a particular screen are no longer needed.
     */

    @Override
    public void dispose() {
    }
}
