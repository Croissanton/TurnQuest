package com.gdx.turnquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.turnquest.dialogs.PreferencesDialog;
import com.gdx.turnquest.dialogs.TutorialDialog;
import com.gdx.turnquest.entities.Clan;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.BaseScreen;
import com.gdx.turnquest.screens.MainMenuScreen;
import com.gdx.turnquest.assets.Assets;
import com.badlogic.gdx.audio.Music;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Stack;

/**
 * This class is the main class of the game.
 * It extends the Game class from libGDX.
 * It is responsible for managing the screens of the game.
 * It also contains some methods that can be used from any class.
 *
 * @author Ignacy
 * @author Cristian
 */
public class TurnQuest extends Game {

	private final Stack<Screen> screenStack = new Stack<>();

	private SpriteBatch batch;

	private static int generalVolume = 50;

	private static Graphics.DisplayMode dm;

	private static OrthographicCamera camera;

	private Stage stage;

	private static Viewport viewport;

	private Player player;

	private Music music;

	public static void playSfx(String filename) {
		Sound sfx = Gdx.audio.newSound(Gdx.files.internal("sfx/" + filename));
		//Default config
		sfx.play(generalVolume/100f);
	}

	public void pushScreen(Screen screen) {
		if (screen != null) {
			screenStack.push(screen);
			setScreen(screen);
		} else {
			Gdx.app.log("TurnQuest", "Screen must not be null");
		}
	}

	public void popScreen() {
		if (!screenStack.isEmpty()) {
			Screen previousScreen = screenStack.pop();
			if (previousScreen != null) {
				previousScreen.dispose();
			}

			if (!screenStack.isEmpty()) {
				Screen newScreen = screenStack.peek();
				if (newScreen instanceof BaseScreen) {
					((BaseScreen) newScreen).refreshScreen();
				}
				setScreen(newScreen);
			}
		} else {
			Gdx.app.log("TurnQuest", "Screen stack is empty");
		}
	}

	public void render() {
		super.render(); // important!
	}

	public void create() {
		Assets.load();
		Assets.ASSET_MANAGER.finishLoading();
		Assets.adjustTitleFont(25);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		setDisplayMode(Gdx.graphics.getDisplayMode());
		getCamera().setToOrtho(false, getVirtualWidth(), getVirtualHeight());
		setViewport(new FitViewport(getVirtualWidth(), getVirtualHeight(), getCamera()));

		pushScreen(new MainMenuScreen(this));
	}

	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

	public static int getGeneralVolume(){
		return generalVolume;
	}

	public void setGeneralVolume(int vol){
		generalVolume=vol;
		if(music != null) music.setVolume(generalVolume/100f);
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


	public static OrthographicCamera getCamera() {
		return camera;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
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

	public Music getMusic() {
		return music;
	}

	public void setMusic(String filename) {
		//Remove previous music.
		if(music != null) music.dispose();

		music = Gdx.audio.newMusic(Gdx.files.internal("music/" + filename));

		//Default config.
		music.setLooping(true);
		music.setVolume(generalVolume/100f);
		music.play();
	}

	public void showPreferencesDialog() {
		PreferencesDialog dialog = new PreferencesDialog("Options", "", Assets.getSkin(), this);
		dialog.setColor(Color.LIGHT_GRAY);
		dialog.show(getStage());
	}

	public void showTutorialDialog(String tutorial) {
		TutorialDialog dialog = new TutorialDialog("Tutorial", Assets.getSkin(), tutorial);
		dialog.setColor(Color.LIGHT_GRAY);
		dialog.show(getStage());
	}
}
