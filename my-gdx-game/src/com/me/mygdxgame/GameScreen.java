package com.me.mygdxgame;

import player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;

	private SpriteBatch batch;
	private Texture texture;
	private Player player;
	private World world;
	private Matrix4 debugMatrix;
	// Rendering Box2D gfx
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	Matrix4 box2dCamera;

	public GameScreen() {
		// Load assets
		Assets.load();
		// Create Box2d world
		world = new World(new Vector2(0, -10), true);
		// Create player, sending world to be able to create physical body
		player = new Player(world);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		map = new TmxMapLoader().load("environment/trollemap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		createCollisionListener();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		camera.update();

		batch.begin();
		player.draw(batch);
		batch.end();

		debugRenderer.render(world, debugMatrix);
		
		mapRenderer.setView(camera);
		mapRenderer.render();

		// Simulate Box2D world
		world.step(1 / 60f, 6, 2);
	}

	private void createCollisionListener() {
	    world.setContactListener(new ContactListener() {

	    	@Override
	    	public void beginContact(Contact contact) {
	    		Fixture fixtureA = contact.getFixtureA();
	    		Fixture fixtureB = contact.getFixtureB();
	            Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	        }

	        @Override
	        public void endContact(Contact contact) {
	            Fixture fixtureA = contact.getFixtureA();
	            Fixture fixtureB = contact.getFixtureB();
	            Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	        }

	        @Override
	        public void preSolve(Contact contact, Manifold oldManifold) {
	        }

	        @Override
	        public void postSolve(Contact contact, ContactImpulse impulse) {
	        }

	    });
	}
	 
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		debugMatrix = new Matrix4(camera.combined);
		debugMatrix.scale(100f, 100f, 1f);
		// debugMatrix.scale(Constants.BOX_TO_WORLD, Constants.BOX_TO_WORLD,
		// 1f);

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
		mapRenderer.dispose();
		map.dispose();
		batch.dispose();
		texture.dispose();
	}

}
