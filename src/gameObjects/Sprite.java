package gameObjects;

import gameActions.ControlScheme;
import gameReferee.GameApplet;
import gameReferee.Referee;
import shapes.Shape;

public abstract class Sprite extends GameObject {
	Shape hitBox;

	public Sprite(Referee ref, GameApplet gApp, double x, double y,
			ControlScheme cScheme) {
		super(ref, gApp, x, y, cScheme);
	}

	@Override
	protected void fixCollision(GameObject dOb) {
		this.hitBox.fixCollision(dOb);
	}

	@Override
	public void render(GameApplet gapp) {
		this.hitBox.render(gapp);	
	}
	
	public abstract void collidedWith(Sprite other);

}
