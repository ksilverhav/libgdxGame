package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.me.mygdxgame.Assets;

public class Player {
	private Sprite sprite;
	private Body body;
	private World world;
	private int width;
	private int height;
	public Player(World world)
	{
		this.world = world;
		this.height = 32;
		this.width = 32;
		this.sprite = Assets.spritePlayer;
		this.sprite.setPosition(10, 10);
	}
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	public float getX(){
		return body.getPosition().x;
	}
	public float gety(){
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
