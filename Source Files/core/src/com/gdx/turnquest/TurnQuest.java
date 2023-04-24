package com.gdx.turnquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.turnquest.dialogs.PreferencesDialog;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.MainMenuScreen;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TurnQuest extends Game {

	private static SpriteBatch batch;

	private static BitmapFont font;

	private static BitmapFont fontSmall;

	private static AssetManager manager;

	private static int generalVolume = 50;

	private static Graphics.DisplayMode dm;

	private static Texture backgroundTexture;

	private static OrthographicCamera camera;

	private static Stage stage;

	private static Skin skin;

	private static Viewport viewport;
	private static Player player;

	public void render() {
		super.render(); // important!
	}

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		fontSmall = new BitmapFont();
		manager = new AssetManager();
		AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<Skin>("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
		manager.load(skinAssetDescriptor);
		manager.finishLoading();
		skin = TurnQuest.getManager().get(skinAssetDescriptor);
		camera = new OrthographicCamera();
		setDisplayMode(Gdx.graphics.getDisplayMode());
		getCamera().setToOrtho(false, getVirtualWidth(), getVirtualHeight());
		setViewport(new FitViewport(getVirtualWidth(), getVirtualHeight(), getCamera()));

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
	public static Texture getBackgroundTexture() {
		return backgroundTexture;
	}

	public static void setBackgroundTexture(Texture backgroundTexture) {
		TurnQuest.backgroundTexture = backgroundTexture;
	}

	public static OrthographicCamera getCamera() {
		return camera;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		TurnQuest.stage = stage;
	}

	public static Skin getSkin() {
		return skin;
	}
	public static Viewport getViewport() {
		return viewport;
	}

	public static void setViewport(Viewport viewport) {
		TurnQuest.viewport = viewport;
	}

	public static Player getPlayer() { return player; }

	public static void setPlayer(Player player) { TurnQuest.player = player; }

	public static void toggleFullscreen(){
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(TurnQuest.getVirtualWidth()/2, TurnQuest.getVirtualHeight()/2);
			getViewport().update(getVirtualWidth()/2, getVirtualHeight()/2, true);
		} else {
			Gdx.graphics.setFullscreenMode(getDisplayMode());
			getViewport().update(getVirtualWidth(), getVirtualHeight(), true);
		}
	}
	public static boolean hasInternetConnection() {
		try {
			URL url = new URL("https://www.google.com");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.connect();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static void showPreferencesDialog() {
		PreferencesDialog dialog = new PreferencesDialog("Options", "", new Runnable() {
			@Override
			public void run() {

			}
		}, getSkin());
		dialog.setColor(Color.LIGHT_GRAY);
		dialog.show(getStage());
	}
}
