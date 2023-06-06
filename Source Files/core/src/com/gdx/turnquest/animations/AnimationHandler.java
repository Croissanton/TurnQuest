package com.gdx.turnquest.animations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Objects;

/**
 * This class allows the handling of animations.
 * It should be used when more than one animation is needed.
 *
 * @author Cristian
 */

public class AnimationHandler {

    private float timer = 0;
    private boolean looping = true;
    private String current;
    private final HashMap<String, Animation<TextureRegion>> animations = new HashMap<>();

    public void add(String name, Animation<TextureRegion> animation){
        animations.put(name, animation);
    }

    public void setCurrent(String name){
        if (Objects.equals(current, name)) return;
        assert (animations.containsKey(name)) : "No such animation " + name;
        current = name;
        timer = 0;
        looping = false;
    }

    public void setCurrent(String name, boolean looping){
        setCurrent(name);
        this.looping = looping;
    }
    public void setAnimationDuration(long duration){
        animations.get(current).setFrameDuration(duration / ((float) animations.get(current).getKeyFrames().length * 1000));
    }

    public boolean isCurrent(String name){
        return current.equals(name);
    }
    public boolean isFinished(){
        if(animations.get(current) == animations.get("idle")){
            return true;
        }
        return animations.get(current).isAnimationFinished(timer);
    }
    public int frameIndex(){
        return animations.get(current).getKeyFrameIndex(timer);
    }

    public TextureRegion getFrame(){
        timer += Gdx.graphics.getDeltaTime();
        return animations.get(current).getKeyFrame(timer, looping);
    }


    @Override
    public String toString() {
        return "AnimationHandler{" +
                "timer=" + timer +
                ", looping=" + looping +
                ", current='" + current + '\'' +
                ", frame=" + animations.get(current).getKeyFrameIndex(timer) +
                '}';
    }

}