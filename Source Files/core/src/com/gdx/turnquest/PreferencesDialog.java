package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.gdx.turnquest.MainMenuScreen.*;

public class PreferencesDialog extends Dialog {

    public int VOLUME = 30;
    private Runnable yesRunnable;

    public PreferencesDialog(String title, String message, Runnable yesRunnable, Skin skin) {
        super(title, skin);
        this.yesRunnable = yesRunnable;
        text(message);
        final CheckBox fullscreen = new CheckBox("Fullscreen",skin);
        final Slider mainVolume = new Slider(0,100,1,false,skin);

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
