package com.me.mygdxgame.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.me.mygdxgame.GameObject;

public class Platform extends GameObject{
	
	protected Body groundBody;
	
	public Platform(float x, float y, Body groundBody){
		this.groundBody = groundBody;
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
