package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Constant;
import com.me.mygdxgame.GameObject;

public class Player extends GameObject {
	private Sprite sprite;
	private Body body;
	private BodyDef bodyDef;
	@SuppressWarnings("unused")
	private World world;
	private CircleShape circle;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	
	private boolean jump = false;
	private final float JUMP_SPEED = 1.5f;
	private final float MAX_X_SPEED = 1;
	private final float MAX_Y_SPEED = 10;
	private final float MIN_Y_SPEED = 5;
	private final float JUMP_RESTITUTION = 1.2f;
	private final float NORMAL_RESTITUTION = 0.6f;

	public Player(World world) {
		this.world = world;
		this.sprite = Assets.spritePlayer;

		this.sprite.setOrigin(Constant.PLAYER_WIDTH / 2, Constant.PLAYER_HEIGHT / 2);
		// First we create a body definition
		bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't
		// move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(500 * Constant.WORLD_TO_BOX,
				500 * Constant.WORLD_TO_BOX);

		// Create our body in the world using our body definition
		this.body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		circle = new CircleShape();
		circle.setRadius(0.16f);

		// Create a fixture definition to apply our shape to
		fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = NORMAL_RESTITUTION; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData(this);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
		getBody().setFixedRotation(true);
	}

	public void draw(SpriteBatch batch) {
		sprite.setPosition(
				(body.getPosition().x * Constant.BOX_TO_WORLD)
						- (sprite.getWidth() / 2),
				(body.getPosition().y * Constant.BOX_TO_WORLD)
						- (sprite.getHeight() / 2));
		sprite.draw(batch);
	}

	public float getX() {
		return body.getPosition().x;
	}

	public float gety() {
		return body.getPosition().y;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public void generalUpdate(Input input) {
//		if(Math.abs(getBody().getLinearVelocity().y) > MAX_Y_SPEED)
//			getBody().setLinearVelocity(getBody().getLinearVelocity().x, MAX_Y_SPEED*Math.abs(getBody().getLinearVelocity().y)/getBody().getLinearVelocity().y);
//		if(Math.abs(getBody().getLinearVelocity().x) > MAX_X_SPEED)
//			getBody().setLinearVelocity(MAX_X_SPEED*Math.abs(getBody().getLinearVelocity().x)/getBody().getLinearVelocity().x,getBody().getLinearVelocity().y);
		switch (Gdx.app.getType()) {
		case Desktop:
			if (input.isKeyPressed(Keys.D))
				getBody().applyForceToCenter(MAX_X_SPEED, 0, true);
			if (input.isKeyPressed(Keys.A))
				getBody().applyForceToCenter(-MAX_X_SPEED, 0, true);
			if (input.isKeyPressed(Keys.SPACE))
				getBody().getFixtureList().get(0).setRestitution(JUMP_RESTITUTION);
			else
				getBody().getFixtureList().get(0).setRestitution(NORMAL_RESTITUTION);
			break;
		case Android:

			break;
		default:
		}
	}

	@Override
	public void beginContactWith(GameObject gameObject, Vector2 normal) {
		// TODO Auto-generated method stub
//		if(jump)
//			getBody().getFixtureList().get(0).setRestitution(JUMP_RESTITUTION);
//		else
//			getBody().getFixtureList().get(0).setRestitution(NORMAL_RESTITUTION);
	}

	@Override
	public void endContactWith(GameObject gameObject, Vector2 normal) {
			
		
	}
}
