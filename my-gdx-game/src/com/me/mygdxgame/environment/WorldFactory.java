package com.me.mygdxgame.environment;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldFactory {
	
	private BodyDef groundBodyDef;
	private Body groundBody;
	private PolygonShape groundBox;
	private World world;

	public WorldFactory(World world){
		this.world = world;
		groundBodyDef = new BodyDef();
	}
	
	public Platform createPlatform(float x, float y, String shape){
		Platform form = null;
		setupGroundBody(x, y, BodyDef.BodyType.StaticBody);
		
		// Creates different platforms depending on the Shape
		if(shape.equals("L45") || shape.equals("R45")){
			form = new SlopePlatform(x, y, groundBody, shape, groundBox);
		} else if(shape.equals("box")){
			form = new BoxPlatform(x, y, groundBody, groundBox);
		}
		
		groundBody.createFixture(groundBox, 0.0f);
		groundBody.getFixtureList().get(0).setUserData(form);
		
		return form;
	}
	
	private void setupGroundBody(float x, float y, BodyType type){
		groundBodyDef.type = type;
		groundBodyDef.position.set(x, y);
		groundBox = new PolygonShape();
		groundBody = world.createBody(groundBodyDef);
	}
	
}
