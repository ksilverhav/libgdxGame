package com.me.mygdxgame.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.me.mygdxgame.Constant;
import com.me.mygdxgame.GameObject;

public class SlopePlatform extends Platform{
	
	private Vector2[] slopeVector;

	public SlopePlatform(float x, float y, Body groundBody, String type, PolygonShape groundBox) {
		super(x, y, groundBody);
		slopeVector = new Vector2[3];
		groundBox.set(createSlopeVectors(type));
	}
	
	private Vector2[] createSlopeVectors(String type){
		if(type.equals("L45")){
			slopeVector[0] = new Vector2(Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2, Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2);
			slopeVector[1] = new Vector2(Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2, -Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2);
			slopeVector[2] = new Vector2(-Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2, -Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2);
		} else if(type.equals("R45")){
			slopeVector[0] = new Vector2(-Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2, Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2);
			slopeVector[1] = new Vector2(-Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2, -Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2);
			slopeVector[2] = new Vector2(Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2, -Constant.PLAYER_WIDTH*Constant.WORLD_TO_BOX/2);
		}
		return slopeVector;
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
