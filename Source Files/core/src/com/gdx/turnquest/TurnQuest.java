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

	private SpriteBatch batch;

	private BitmapFont font;

	private AssetManager manager;

	private static int generalVolume = 50;

	private static Graphics.DisplayMode dm;

	private Texture backgroundTexture;

	private static OrthographicCamera camera;

	private Stage stage;

	private Skin skin;

	private static Viewport viewport;
	private Player player;

	public void render() {
		super.render(); // important!
	}

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		manager = new AssetManager();
		backgroundTexture = new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png"));
		AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<>("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
		manager.load(skinAssetDescriptor);
		manager.finishLoading();
		skin = manager.get(skinAssetDescriptor);
		camera = new OrthographicCamera();
		setDisplayMode(Gdx.graphics.getDisplayMode());
		getCamera().setToOrtho(false, getVirtualWidth(), getVirtualHeight());
		setViewport(new FitViewport(getVirtualWidth(), getVirtualHeight(), getCamera()));

		this.setScreen(new MainMenuScreen(this));
	}

	public void dispose() {
		manager.dispose();
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

	public SpriteBatch getBatch(){
		return batch;
	}

	public BitmapFont getFont(){
		return font;
	}

	public Texture getBackgroundTexture() {
		return backgroundTexture;
	}

	public void setBackgroundTexture(Texture backgroundTexture) {
		backgroundTexture.load(backgroundTexture.getTextureData());
	}

	public static OrthographicCamera getCamera() {
		return camera;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Skin getSkin() {
		return skin;
	}
	public static Viewport getViewport() {
		return viewport;
	}

	public static void setViewport(Viewport viewport) {
		TurnQuest.viewport = viewport;
	}

	public Player getCurrentPlayer() { return player; }

	public void setCurrentPlayer(Player player) { this.player = player; }

	public static void toggleFullscreen(){
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(getVirtualWidth()/2, getVirtualHeight()/2);
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

	public void showPreferencesDialog() {
		PreferencesDialog dialog = new PreferencesDialog("Options", "", () -> {

		}, getSkin());
		dialog.setColor(Color.LIGHT_GRAY);
		dialog.show(getStage());
	}
}
