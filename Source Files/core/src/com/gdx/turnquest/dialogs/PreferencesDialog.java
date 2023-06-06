package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.turnquest.TurnQuest;
/**
 * This class shows the preferences' dialog.
 * There you can set the music and sfx volume and turn on/off fullscreen.
 *
 * @author Mikolaj
 */
public class PreferencesDialog extends Dialog {

    public PreferencesDialog(String title, String message, Skin skin, TurnQuest game) {
        super(title, skin);
        text(message);
        final CheckBox fullscreen = new CheckBox("Fullscreen",skin);
        final CheckBox mute = new CheckBox("Mute",skin);
        final Slider sliderVolume = new Slider(0,100,1,false,skin);
        TextButton bBack = new TextButton("Back", skin);

        final TextField tTest = new TextField("VOLUME: "+ TurnQuest.getGeneralVolume(), skin);
        tTest.setDisabled(true);

        // Set initial value of mainVolume slider
        sliderVolume.setValue(TurnQuest.getGeneralVolume());

        // Set initial state of fullscreen checkbox based on current full screen state
        fullscreen.setChecked(Gdx.graphics.isFullscreen());

        getContentTable().defaults().pad(10);
        getContentTable().row();
        getContentTable().add(fullscreen).left().padLeft(30);
        getContentTable().row();
        getContentTable().add(sliderVolume).width(400);
        getContentTable().add(tTest).width(235);
        getContentTable().add(mute).width(200);
        getContentTable().row();
        getContentTable().add(bBack).center().width(400).colspan(3);

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
                game.setGeneralVolume((int) sliderVolume.getValue());
                tTest.setText(" VOLUME: " + (int)sliderVolume.getValue());
            }
        });

        mute.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(mute.isChecked()){
                    game.setGeneralVolume(0);
                    sliderVolume.setValue(0);
                    tTest.setText(" VOLUME: " + (int)sliderVolume.getValue());
                }
                else{
                    game.setGeneralVolume(50);
                    sliderVolume.setValue(50);
                    tTest.setText(" VOLUME: " + (int)sliderVolume.getValue());
                }
            }
        });
    }


    @Override
    protected void result(Object object) {
        hide();
    }
}
