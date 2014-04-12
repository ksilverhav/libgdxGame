package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Assets {
	public static Sprite spritePlayer;
	public static void load(){
		spritePlayer = new Sprite(new Texture(Gdx.files.internal("player/player.png")));
	}
}
