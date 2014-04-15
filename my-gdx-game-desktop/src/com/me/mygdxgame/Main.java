package com.me.mygdxgame;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.title = "my-gdx-game";
		cfg.useGL20 = true;

		cfg.width = 1920;
		cfg.height = 1080;
		cfg.fullscreen = true;
		
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
