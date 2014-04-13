package com.me.mygdxgame.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.me.mygdxgame.Constant;
import com.me.mygdxgame.GameObject;

public class Platform extends GameObject {

	private BodyDef groundBodyDef;
	private Body groundBody;
	private PolygonShape groundBox;

	@SuppressWarnings("unused")
	private float x, y;

	public Platform(float x, float y, World world) {
		this.x = x;
		this.y = y;
		groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(x, y);
		groundBox = new PolygonShape();
		groundBody = world.createBody(groundBodyDef);
		groundBox.setAsBox((32 * Constant.WORLD_TO_BOX) / 2,
				(32 * Constant.WORLD_TO_BOX) / 2);
		groundBody.createFixture(groundBox, 0.0f);
		groundBody.getFixtureList().get(0).setUserData(this);
	}

	@Override
	public void beginContactWith(GameObject gameObject, Vector2 normal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContactWith(GameObject gameObject, Vector2 normal) {
		// TODO Auto-generated method stub

	}

}
