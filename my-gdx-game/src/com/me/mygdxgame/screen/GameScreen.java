package com.me.mygdxgame.screen;

import java.util.ArrayList;

import player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Constant;
import com.me.mygdxgame.GameObject;
import com.me.mygdxgame.MapBodyManager;
import com.me.mygdxgame.environment.Platform;
import com.me.mygdxgame.environment.WorldFactory;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;
	private TiledMapTileLayer blocks;
	
	private ArrayList<Platform> platforms;

	private SpriteBatch batch;
	private Texture texture;
	private Player player;
	private World world;
	private WorldFactory worldFactory;
	private Matrix4 debugMatrix;
	// Rendering Box2D gfx
	private Box2DDebugRenderer debugRenderer;
	boolean debug = false;
	// Which nr of map is it
	private double mapX = 0;
	private double mapY = 0;
	// Screen resolution
	Vector2 screenResolution;
	//Shaderprogram
	ShaderProgram shader;
	int a; // Uniform ID
	int b; // Uniform ID
	
	public GameScreen() {
		// Load assets
		Assets.load();
		// Create Box2d world
		world = new World(new Vector2(0, -20), true);
		worldFactory = new WorldFactory(world);
		// Create player, sending world to be able to create physical body
		player = new Player(world);
		// ArrayList that holds all platforms
		platforms = new ArrayList<Platform>();
		// Loading the testmap
		map = new TmxMapLoader().load("environment/mud-test.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		createMap(map);
		//createObjectsFromMap(map);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		createCollisionListener();
		
		debugRenderer = new Box2DDebugRenderer();
		
		screenResolution = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.scale(10);
	}
	
	BitmapFont font = new BitmapFont();
	@Override
	public void render(float delta) {
		// Shader
		shader.begin();
		shader.setUniformf(a ,player.getBody().getPosition().x*Constant.BOX_TO_WORLD, player.getBody().getPosition().y*Constant.BOX_TO_WORLD,0);
		shader.setUniformf(b , screenResolution);
		shader.end();
		// End of shader stuffs
		
		Gdx.gl.glClearColor(0.2f, 0.3f, 0.34f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		generalUpdate();
		camera.update();

		batch.begin();
		batch.disableBlending();
		Assets.spriteBackground.draw(batch);
		Assets.spriteBackground2.draw(batch);

		batch.enableBlending();
		
		player.draw(batch);
		
		
		font.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 300, 1000);
		
		batch.end();
		
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		if(debug)
			debugRenderer.render(world, debugMatrix);
		// Simulate Box2D world
		world.step(1 / 60f, 6, 2);
	}
	double playerMapX; 
	private void generalUpdate() {
		player.generalUpdate(Gdx.input);
		playerMapX = Math.floor(player.getBody().getPosition().x*Constant.BOX_TO_WORLD/1920);
		if(playerMapX != mapX)
		{
			if(playerMapX > mapX)
			{
				camera.translate(new Vector2(1920,0));
				mapX++;
			}
			else
			{
				camera.translate(new Vector2(-1920,0));
				mapX--;
			}
			camera.update();
			debugMatrix = new Matrix4(camera.combined);
			debugMatrix.scale(100f, 100f, 1f);
			
		}
		// Turn on and off Box2D debugging
		if(Gdx.input.isKeyPressed(Keys.F1))
			debug =false;
		if(Gdx.input.isKeyPressed(Keys.F2))
			debug =true;
	}

	private void createCollisionListener() {
		
	    world.setContactListener(new ContactListener() {
	    	Fixture fixtureA;
	    	Fixture fixtureB;
	    	@Override
	    	public void beginContact(Contact contact) {
	    		player.beginContact(contact, Gdx.input);
	    		fixtureA = contact.getFixtureA();
	    		fixtureB = contact.getFixtureB();
	    		((GameObject) fixtureA.getUserData()).beginContactWith((GameObject) fixtureB.getUserData(), contact.getWorldManifold().getPoints()[0]);
	    		((GameObject) fixtureB.getUserData()).beginContactWith((GameObject) fixtureA.getUserData(), contact.getWorldManifold().getPoints()[0]);
	        }

	    	@Override
	        public void endContact(Contact contact) {
	    		player.endContact(contact, Gdx.input);
	            fixtureA = contact.getFixtureA();
	            fixtureB = contact.getFixtureB();
	    		((GameObject) fixtureA.getUserData()).endContactWith((GameObject) fixtureB.getUserData(), contact.getWorldManifold().getPoints()[0]);
	    		((GameObject) fixtureB.getUserData()).endContactWith((GameObject) fixtureA.getUserData(), contact.getWorldManifold().getPoints()[0]);
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
		screenResolution = new Vector2(width,height);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		debugMatrix = new Matrix4(camera.combined);
		debugMatrix.scale(100f, 100f, 1f);
		// debugMatrix.scale(Constants.BOX_TO_WORLD, Constants.BOX_TO_WORLD,
		// Shader
		shader = new ShaderProgram(Gdx.files.internal("shaders/vertexshader.vs"), Gdx.files.internal("shaders/fragmentshader.fs"));
		if(!shader.isCompiled()){
		    String log = shader.getLog();
		    System.out.println(log);
		}
		
		a = shader.getUniformLocation("u_playerPos");
		b = shader.getUniformLocation("u_resolution");
		//ShaderProgram.pedantic = false;
		// Map Renderer
		mapRenderer.getSpriteBatch().setShader(shader);
		
		// Sprite batch
		batch.setShader(shader);

		

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
	private void createMap(TiledMap map)
	{
		(new MapBodyManager(world,100f,0)).createPhysics(map);
	}
	private void createObjectsFromMap(TiledMap map){blocks = (TiledMapTileLayer) map.getLayers().get("Tile Layer 1");
	for(int x = 0; x < blocks.getWidth(); x++){
		for(int y = 0; y < blocks.getHeight(); y++){
			if(blocks.getCell(x, y) != null){
				// Checks the properties of the tile and creates a platform if the type is "solid"
				if(blocks.getCell(x, y).getTile().getProperties().get("type").equals("platform")){
					addPlatform(worldFactory.createPlatform((x*Constant.WORLD_TO_BOX*32)+(16*Constant.WORLD_TO_BOX), (y*Constant.WORLD_TO_BOX*32)+(16*Constant.WORLD_TO_BOX), (String) blocks.getCell(x, y).getTile().getProperties().get("shape")));
				}
				
			}
		}
	}
}
	private void addPlatform(Platform platform){
		platforms.add(platform);
	}

}

