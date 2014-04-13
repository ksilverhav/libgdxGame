package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	public abstract void beginContactWith(GameObject gameObject, Vector2 vector2);
	public abstract void endContactWith(GameObject gameObject, Vector2 vector2);
}
