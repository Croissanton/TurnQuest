package com.gdx.turnquest.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.screens.*;

/**
 * The Assets class is a singleton class that provides access to the AssetManager and contains constants for all assets
 * used in the game.
 */
public class Assets {
    public static final AssetManager ASSET_MANAGER = new AssetManager();

    public static final String FOREST_BACKGROUND_PNG = "Pixel art forest/Preview/Background.png";

    public static final String SKIN_PIXTHULHU_UI_JSON = "pixthulhu/skin/pixthulhu-ui.json";

    public static BitmapFont titleFont;

    public static void loadFor(Class<?> screenClass) {
        if (screenClass == null) {
            throw new IllegalArgumentException("Screen class cannot be null.");
        } else if (screenClass == InventoryScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == GameScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == AbilitiesScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == CharactersScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == ClanScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == CombatScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == MainMenuScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == ShopScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else {
            throw new IllegalArgumentException("Screen class not recognized.");
        }
    }

    public static Skin getSkin() {
        return Assets.ASSET_MANAGER.get(Assets.SKIN_PIXTHULHU_UI_JSON, Skin.class);
    }

    public static Texture getBackgroundTexture(String bg) {
        return Assets.ASSET_MANAGER.get(bg, Texture.class);
    }

    public static void setBackgroundTexture(Texture backgroundTexture) {
        backgroundTexture.load(backgroundTexture.getTextureData());
    }

    public static BitmapFont getFont(){
        return Assets.ASSET_MANAGER.get(Assets.SKIN_PIXTHULHU_UI_JSON, Skin.class).getFont("font");
    }
    public static void adjustTitleFont(int spacing){
        BitmapFont badFont = ASSET_MANAGER.get(Assets.SKIN_PIXTHULHU_UI_JSON, Skin.class).getFont("title");
        BitmapFont adjustedFont = new BitmapFont(badFont.getData(), badFont.getRegion(), badFont.usesIntegerPositions());
        for (BitmapFont.Glyph[] glyphs : adjustedFont.getData().glyphs) {
            if (glyphs != null) {
                for (BitmapFont.Glyph glyph : glyphs) {
                    if (glyph != null) {
                        glyph.xadvance += spacing;
                    }
                }
            }
        }
        titleFont = adjustedFont;
    }

    public static BitmapFont getTitleFont(){
        return titleFont;
    }



    public static void load() {
        ASSET_MANAGER.load(SKIN_PIXTHULHU_UI_JSON, Skin.class);
    }

    public static void dispose() {
        ASSET_MANAGER.dispose();
    }
}
