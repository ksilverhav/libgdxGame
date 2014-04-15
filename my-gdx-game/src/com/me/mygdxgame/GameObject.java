package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject {
	
	protected Sprite sprite;
	protected Body body;
	
	public abstract void beginContactWith(GameObject gameObject, Vector2 vector2);
	
	public abstract void endContactWith(GameObject gameObject, Vector2 vector2);
	
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
}
