package com.gdx.turnquest.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private Map<String, Animation<TextureRegion>> animations;
    private float elapsedTime;

    public AnimationManager(Texture spriteSheet, int frameCols, int frameRows, Map<String, int[]> animationsInfo, Map<String, Float> durations) {
        this.animations = new HashMap<String, Animation<TextureRegion>>();

        TextureRegion[][] tempFrames = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / frameCols,
                spriteSheet.getHeight() / frameRows);

        for (String animationName : animationsInfo.keySet()) {
            int[] frameIndices = animationsInfo.get(animationName);
            float frameDuration = durations.get(animationName);
            Array<TextureRegion> animationFrames = new Array<TextureRegion>(frameIndices.length);

            for (int frameIndex : frameIndices) {
                animationFrames.add(tempFrames[frameIndex / frameCols][frameIndex % frameCols]);
            }

            Animation<TextureRegion> animation = new Animation<TextureRegion>(frameDuration, animationFrames);
            this.animations.put(animationName, animation);
        }
    }

    public void update(float deltaTime) {
        elapsedTime += deltaTime;
    }

    public void render(SpriteBatch spriteBatch, float x, float y, String animationName) {
        spriteBatch.draw(animations.get(animationName).getKeyFrame(elapsedTime, true), x, y);
    }
}
