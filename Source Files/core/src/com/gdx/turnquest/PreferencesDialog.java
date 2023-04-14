package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
public class PreferencesDialog extends Dialog {
    private Runnable yesRunnable;


    public PreferencesDialog(String title, String message, Runnable yesRunnable, Skin skin) {
        super(title, skin);
        this.yesRunnable = yesRunnable;
        text(message);
        final CheckBox fullscreen = new CheckBox("Fullscreen",skin);
        final Slider sliderVolume = new Slider(0,100,1,false,skin);
        TextButton bBack = new TextButton("Back", skin);

        final TextField tTest = new TextField(" VOLUME: "+TurnQuest.getGeneralVolume(), skin);
        tTest.setDisabled(true);

        // Set initial value of mainVolume slider
        sliderVolume.setValue(TurnQuest.getGeneralVolume());

        // Set initial state of fullscreen checkbox based on current full screen state
        fullscreen.setChecked(Gdx.graphics.isFullscreen());

        getContentTable().defaults().pad(10);
        getContentTable().add(fullscreen).width(400);
        getContentTable().row();
        getContentTable().add(sliderVolume).width(400);
        getContentTable().add(bBack).width(200);
        getContentTable().add(tTest).width(235);

        fullscreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                TurnQuest.toggleFullscreen();
            }
        });

        bBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                hide();
            }
        });

        sliderVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                TurnQuest.setGeneralVolume((int) sliderVolume.getValue());
                tTest.setText(" VOLUME: " + (int)sliderVolume.getValue());
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
