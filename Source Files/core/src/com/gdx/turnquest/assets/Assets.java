package com.gdx.turnquest.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


//TODO: finish this, complete with other assets, implementing this instead of assetmanager in TurnQuest class.
public class Assets {
    public static AssetManager manager = new AssetManager();

    public void load() {
        manager.load("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
    }

    public void dispose() {
        manager.dispose();
    }

    public AssetManager getManager() {
        return manager;
    }
}
