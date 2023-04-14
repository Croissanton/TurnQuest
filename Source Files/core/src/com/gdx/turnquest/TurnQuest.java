package com.gdx.turnquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TurnQuest extends Game {

	private static SpriteBatch batch;

	private static BitmapFont font;

	private static BitmapFont fontSmall;

	private static AssetManager manager;

	private static int generalVolume = 50;

	private static Graphics.DisplayMode dm;


	public void render() {
		super.render(); // important!
	}

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		fontSmall = new BitmapFont();
		manager = new AssetManager();

		this.setScreen(new MainMenuScreen(this));
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public static int getGeneralVolume(){
		return generalVolume;
	}

	public static void setGeneralVolume(int vol){
		generalVolume=vol;
	}

	public static Graphics.DisplayMode getDisplayMode(){
		return dm;
	}

	public static void setDisplayMode(Graphics.DisplayMode displayMode){
		dm = displayMode;
	}

	public static int getVirtualWidth(){
		return dm.width;
	}

	public static int getVirtualHeight(){
		return dm.height;
	}

	public static SpriteBatch getBatch(){
		return batch;
	}

	public static BitmapFont getFont(){
		return font;
	}

	public static BitmapFont getFontSmall(){
		return fontSmall;
	}

	public static AssetManager getManager(){
		return manager;
	}
}
