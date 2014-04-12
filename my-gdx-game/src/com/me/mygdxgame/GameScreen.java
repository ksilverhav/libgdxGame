package com.me.mygdxgame;

import player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Player player;
	private World world;
	private Matrix4 debugMatrix;
	// Rendering Box2D gfx
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	Matrix4 box2dCamera;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		
	
		camera.update();		
		
		batch.begin();
		player.draw(batch);
		batch.end();
		
		debugRenderer.render(world, debugMatrix);
		
		
		// Simulate Box2D world
		world.step(1/60f, 6, 2);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {		
		// Load assets
		Assets.load();
		// Create Box2d world
		world = new World(new Vector2(0,-10), true);
		// Create player, sending world to be able to create physical body
		player = new Player(world);
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		
		batch = new SpriteBatch();
		
		
		debugMatrix=new Matrix4(camera.combined);
		debugMatrix.scale(100f,100f, 1f);
		//debugMatrix.scale(Constants.BOX_TO_WORLD, Constants.BOX_TO_WORLD, 1f);
		
		
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
		batch.dispose();
		texture.dispose();
	}

}
