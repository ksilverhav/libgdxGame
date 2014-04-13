package com.me.mygdxgame.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.me.mygdxgame.Constant;
import com.me.mygdxgame.GameObject;

public class BoxPlatform extends Platform {

	public BoxPlatform(float x, float y, Body groundBody, PolygonShape groundBox) {
		super(x, y, groundBody);
		groundBox.setAsBox((32 * Constant.WORLD_TO_BOX) / 2,
				(32 * Constant.WORLD_TO_BOX) / 2);
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
