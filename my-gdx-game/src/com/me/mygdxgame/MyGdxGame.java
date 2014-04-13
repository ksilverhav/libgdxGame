package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.me.mygdxgame.screen.GameScreen;

public class MyGdxGame extends Game {
	@Override
	public void create() {	 
		this.setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
