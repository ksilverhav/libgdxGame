package com.me.mygdxgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.mygdxgame.Assets;

public class MenuScreen implements Screen {
	Game game;
	private BitmapFont font;
	SpriteBatch batch;
	public MenuScreen(Game game)
	{
		this.game = game;
		font = new BitmapFont();
		batch = new SpriteBatch();
		font.scale(9f);
	}
	@Override
	public void render(float delta) {
		generalUpdate();
		batch.begin();
		Assets.spriteBackground.draw(batch);
		font.draw(batch, "trolle saga", 600, 700);
		font.draw(batch, "Press F1 + F7 to continue", 100, 500);
		batch.end();
		
	}

	private void generalUpdate() {
		if(Gdx.input.isKeyPressed(Keys.F1) && Gdx.input.isKeyPressed(Keys.F7))
			game.setScreen(new GameScreen());
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
