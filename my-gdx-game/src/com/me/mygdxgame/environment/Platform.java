package com.me.mygdxgame.environment;

import com.badlogic.gdx.physics.box2d.Body;
import com.me.mygdxgame.GameObject;

public class Platform extends GameObject{
	
	protected Body groundBody;
	
	public Platform(float x, float y, Body groundBody){
		this.groundBody = groundBody;
		 
	}

	@Override
	public void beginContactWith(GameObject gameObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContactWith(GameObject gameObject) {
		// TODO Auto-generated method stub
		
	}

}
