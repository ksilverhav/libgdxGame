package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Assets {
	public static Sprite spritePlayer;
	public static Sprite spriteBackground;
	public static Sprite spriteBackground2;
	public static void load(){
		spritePlayer = new Sprite(new Texture(Gdx.files.internal("player/player.png")));
		spriteBackground = new Sprite(new Texture(Gdx.files.internal("environment/background.png")));
		spriteBackground2 = new Sprite(new Texture(Gdx.files.internal("environment/background2.png")));
		spriteBackground2.setPosition(1920, 0);
	}
}

