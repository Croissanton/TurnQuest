package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.gdx.turnquest.MainMenuScreen.*;

public class PreferencesDialog extends Dialog {

    public int MAINVOLUME = 30;
    private Runnable yesRunnable;

    public PreferencesDialog(String title, String message, Runnable yesRunnable, Skin skin) {
        super(title, skin);
        this.yesRunnable = yesRunnable;
        text(message);
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

        bBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                hide();
            }
        });

        mainVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MAINVOLUME=(int) mainVolume.getPercent();

            }
        });
    }



    @Override
    protected void result(Object object) {
        boolean result = (boolean) object;
        if (result) {
            yesRunnable.run();
        }
        hide();
    }
}
