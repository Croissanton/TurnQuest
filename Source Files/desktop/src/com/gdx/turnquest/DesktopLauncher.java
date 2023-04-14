package com.gdx.turnquest;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setResizable(false); // Disable window resizing
		//We could set an icon here with config.setWindowIcon("icon.png");
		config.setTitle("turn-quest");
		Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
		//To start in windowed mode => config.setWindowedMode(dm.width/2, dm.height/2);
		config.setFullscreenMode(dm);
		new Lwjgl3Application(new TurnQuest(), config);
	}
}
