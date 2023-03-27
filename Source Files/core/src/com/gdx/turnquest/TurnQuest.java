package com.gdx.turnquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TurnQuest extends Game {

	public SpriteBatch batch;

	public BitmapFont font;

	public BitmapFont fontSmall;

	public AssetManager manager;


	public void show(){

	}

	public void render() {
		super.render(); // important!
	}

	public void resize(int width, int height){

	}

	public void pause(){

	}

	public void resume(){

	}

	public void hide(){

	}


	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		fontSmall = new BitmapFont();
		manager = new AssetManager();

		FitViewport fitViewport = new FitViewport(800,800);
		this.setScreen(new MainMenuScreen(this));
	}


	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
