package com.gdx.turnquest.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.screens.*;

/**
 * The Assets class is a singleton class that provides access to the AssetManager and contains constants for all assets
 * used in the game.
 */
public class Assets {
    public static final AssetManager ASSET_MANAGER = new AssetManager();

    public static final String FOREST_BACKGROUND_PNG = "backgrounds/forest.png";
    public static final String ARCHER_IDLE_PNG = "animations/archer/archer_idle.png";
    public static final String ARCHER_PORTRAIT_PNG = "animations/archer/archer_portrait.png";
    public static final String WARRIOR_IDLE_PNG = "animations/warrior/warrior_idle.png";
    public static final String WARRIOR_PORTRAIT_PNG = "animations/warrior/warrior_portrait.png";
    public static final String MAGE_IDLE_PNG = "animations/mage/mage_idle.png";
    public static final String MAGE_PORTRAIT_PNG = "animations/mage/mage_portrait.png";
    public static final String SKIN_PIXTHULHU_UI_JSON = "pixthulhu/skin/pixthulhu-ui.json";
    public static final String SHOP_BACKGROUND_PNG = "backgrounds/shop.png";


    public static BitmapFont titleFont;

    /**
     * Loads the assets for the specified screen.
     * @param screenClass the screen class to load assets for
     */

    public static void loadFor(Class<?> screenClass) {
        if (screenClass == null) {
            throw new IllegalArgumentException("Screen class cannot be null.");
        } else if (screenClass == InventoryScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == GameScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == AbilitiesScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == ClanScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == CombatScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == MainMenuScreen.class) {
            ASSET_MANAGER.load(FOREST_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == ShopScreen.class) {
            ASSET_MANAGER.load(SHOP_BACKGROUND_PNG, Texture.class);
        } else if (screenClass == PlayerScreen.class) {
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

    public static void loadCharacterTexture(String characterClass) {
        switch (characterClass) {
            case "Warrior":
                ASSET_MANAGER.load(WARRIOR_IDLE_PNG, Texture.class);
                break;
            case "Mage":
                ASSET_MANAGER.load(MAGE_IDLE_PNG, Texture.class);
                break;
            case "Archer":
                ASSET_MANAGER.load(ARCHER_IDLE_PNG, Texture.class);
                break;
            default:
                throw new IllegalArgumentException("Character class not recognized.");
        }
    }

    public static Texture getCharacterTexture(String characterClass) {
        switch (characterClass) {
            case "Warrior":
                return Assets.ASSET_MANAGER.get(WARRIOR_IDLE_PNG, Texture.class);
            case "Mage":
                return Assets.ASSET_MANAGER.get(MAGE_IDLE_PNG, Texture.class);
            case "Archer":
                return Assets.ASSET_MANAGER.get(ARCHER_IDLE_PNG, Texture.class);
            default:
                throw new IllegalArgumentException("Character class not recognized.");
        }
    }

    public static void loadCharacterPortrait(String characterClass) {
        switch (characterClass) {
            case "Warrior":
                ASSET_MANAGER.load(WARRIOR_PORTRAIT_PNG, Texture.class);
                break;
            case "Mage":
                ASSET_MANAGER.load(MAGE_PORTRAIT_PNG, Texture.class);
                break;
            case "Archer":
                ASSET_MANAGER.load(ARCHER_PORTRAIT_PNG, Texture.class);
                break;
            default:
                throw new IllegalArgumentException("Character class not recognized.");
        }
    }

    public static Texture getCharacterPortrait(String characterClass) {
        switch (characterClass) {
            case "Warrior":
                return Assets.ASSET_MANAGER.get(WARRIOR_PORTRAIT_PNG, Texture.class);
            case "Mage":
                return Assets.ASSET_MANAGER.get(MAGE_PORTRAIT_PNG, Texture.class);
            case "Archer":
                return Assets.ASSET_MANAGER.get(ARCHER_PORTRAIT_PNG, Texture.class);
            default:
                throw new IllegalArgumentException("Character class not recognized.");
        }
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

    public static BitmapFont getSubtitleFont() {return Assets.ASSET_MANAGER.get(Assets.SKIN_PIXTHULHU_UI_JSON, Skin.class).getFont("subtitle");}

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
