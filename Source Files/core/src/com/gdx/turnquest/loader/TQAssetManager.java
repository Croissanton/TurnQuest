package com.gdx.turnquest.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TQAssetManager {
    public final AssetManager manager = new AssetManager();

    public final String font = "/assets/pixthulhu/skin/font-export.fnt";
    public final String skin = "/assets/pixthulhu/skin/pixthulhu-ui.json";
    public final String atlas = "/assets/pixthulhu/skin/pixthulhu-ui.atlas";

    public void queueAssets() {
        manager.load(font, BitmapFont.class);
        manager.load(skin, Skin.class);
        manager.load(atlas, TextureAtlas.class);
    }
}
