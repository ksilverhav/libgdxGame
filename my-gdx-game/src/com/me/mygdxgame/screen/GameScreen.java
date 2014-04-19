package com.me.mygdxgame.screen;

import player.Player;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

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

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;

	private SpriteBatch batch;
	private Texture texture;
	private Player player;
	private World world;
	private Matrix4 debugMatrix;
	private BitmapFont font;
	// Rendering Box2D gfx
	private Box2DDebugRenderer debugRenderer;
	boolean debug = false;
	// Which nr of map is it
	private double mapX = 0;
	private double mapY = 0;
	private double playerMapX;
	private double playerMapY;
	// Screen resolution
	private Vector2 screenResolution;
	//Shaderprogram
	private ShaderProgram shader;
	int a; // Uniform ID
	int b; // Uniform ID
	
	public GameScreen() {
		// Create Box2d world
		world = new World(new Vector2(0, -20), true);
		// Create player, sending world to be able to create physical body
		player = new Player(world);
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
		
		font = new BitmapFont();
		font.scale(10);
		//texture = new Texture(Gdx.files.internal("environment/shadowmap.png"));
	}
	
	
	@Override
	public void render(float delta) {
		// Shader
		//Shader test
		
				//texture.bind(1);
		//shader.begin();
		//shader.setUniformf(a ,player.getBody().getPosition().x*Constant.BOX_TO_WORLD, player.getBody().getPosition().y*Constant.BOX_TO_WORLD,0);
		//shader.setUniformf(b , screenResolution);
		
		
		
		
		//shader.setUniformi("u_shadowmap", 1);
		
		
		// Shader test stop
		
		
		//shader.end();
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
		batch.end();
		
		//Draw map behind player
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		batch.begin();
		player.draw(batch);
		
		
		font.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 300, 1000);
		
		batch.end();
		rayHandler.updateAndRender();
		
		
		if(debug)
			debugRenderer.render(world, debugMatrix);
		// Simulate Box2D world
		
		world.step(1 / 60f, 6, 2);
		
	}
	
	private void generalUpdate() {
		player.generalUpdate(Gdx.input);
		// Update if new map is to be loaded
		updateMap();
		
		// Turn on and off Box2D debugging
		if(Gdx.input.isKeyPressed(Keys.F1))
			debug =false;
		if(Gdx.input.isKeyPressed(Keys.F2))
			debug =true;
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
			Gdx.app.exit();
		
	}

	private void updateMap() {
		playerMapX = Math.floor(player.getBody().getPosition().x*Constant.BOX_TO_WORLD/Gdx.graphics.getWidth());
		playerMapY = Math.floor(player.getBody().getPosition().y*Constant.BOX_TO_WORLD/Gdx.graphics.getHeight());
		if(playerMapX != mapX)
		{
			if(playerMapX > mapX)
			{
				camera.translate(new Vector2(Gdx.graphics.getWidth(),0));
				mapX++;
			}
			else
			{
				camera.translate(new Vector2(-Gdx.graphics.getWidth(),0));
				mapX--;
			}
			camera.update();
			debugMatrix = new Matrix4(camera.combined);
			debugMatrix.scale(100f, 100f, 1f);
			
		}
		if(playerMapY != mapY)
		{
			if(playerMapY > mapY)
			{
				camera.translate(new Vector2(Gdx.graphics.getHeight(),0));
				mapY++;
			}
			else
			{
				camera.translate(new Vector2(-Gdx.graphics.getHeight(),0));
				mapY--;
			}
			
			camera.update();
			debugMatrix = new Matrix4(camera.combined);
			debugMatrix.scale(100f, 100f, 1f);
			
		}
		
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
	RayHandler rayHandler;
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
		
		
		//light test
		
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(debugMatrix);
		rayHandler.setShadows(true);
		
		
//		new PointLight(rayHandler, 128, new Color(1,1,1,1), 10f, 600*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setStaticLight(true);
		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 900*Constant.WORLD_TO_BOX, 1000*Constant.WORLD_TO_BOX).setXray(true);
		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 1100*Constant.WORLD_TO_BOX, 1000*Constant.WORLD_TO_BOX).setXray(true);
		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 1300*Constant.WORLD_TO_BOX, 1000*Constant.WORLD_TO_BOX).setXray(true);
		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 1500*Constant.WORLD_TO_BOX, 1000*Constant.WORLD_TO_BOX).setXray(true);
		
		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 1200*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setStaticLight(true);
		new PointLight(rayHandler, 128, new Color(1,1,1,0.7f), 10f, 1400*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setStaticLight(false);
		
		new PointLight(rayHandler, 128, new Color(1,1,1,0.9f), 10f, 250*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setStaticLight(true);
		
		new PointLight(rayHandler, 128, new Color(1,1,1,0.9f), 10f, 250*Constant.WORLD_TO_BOX, 1000*Constant.WORLD_TO_BOX).setStaticLight(true);
		
		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 700*Constant.WORLD_TO_BOX, 800*Constant.WORLD_TO_BOX).setStaticLight(true);
		
		
//		new PointLight(rayHandler, 128, new Color(1,0.4f,0,1), 10f, 100*Constant.WORLD_TO_BOX, 900*Constant.WORLD_TO_BOX).setStaticLight(true);
//		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 10f, 750*Constant.WORLD_TO_BOX, 800*Constant.WORLD_TO_BOX).setStaticLight(true);
//		new PointLight(rayHandler, 128, new Color(1,1,1,1), 10f, 1800*Constant.WORLD_TO_BOX, 800*Constant.WORLD_TO_BOX).setStaticLight(true);
//		new PointLight(rayHandler, 128, new Color(1,1,1,1f), 1f, 1800*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setDirection(0.3f);
//		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 1f, 1200*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setStaticLight(true);
//		new PointLight(rayHandler, 128, new Color(1,1,1,0.5f), 1f, 1500*Constant.WORLD_TO_BOX, 250*Constant.WORLD_TO_BOX).setStaticLight(true);
		 
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
		rayHandler.dispose();
	}
	
	private void createMap(TiledMap map)
	{
		(new MapBodyManager(world,100f,0)).createPhysics(map);
	}
}

