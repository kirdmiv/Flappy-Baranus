package com.kirdmiv.flappybaranus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kirdmiv.flappybaranus.FlappyBaran;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = FlappyBaran.HEIGHT;
		config.width = FlappyBaran.WIDTH;
		config.title = FlappyBaran.TITLE;
		new LwjglApplication(new FlappyBaran(), config);
	}
}
