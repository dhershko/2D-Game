package gameObjects;

import java.util.HashMap;
import java.util.List;

import polygons.Point;
import gameActions.ControlScheme;
import gameActions.GameAction;
import gameReferee.GameApplet;
import gameReferee.Referee;

public abstract class DynamicObject extends GameObject{
	public float xVel = 0;
	public float yVel = 0;
	public float x;
	public float y;
	public float xAcc = 0;
	public float yAcc = 0;
	public ControlScheme cScheme;

	public DynamicObject(Referee ref, GameApplet gApp, float x, float y, ControlScheme cScheme) {
		super(ref, gApp);
		this.x = x;
		this.y = y;
		this.cScheme = cScheme;
	}	

	protected void translate(double dX, double dY) {
		this.x += dX;
		this.y += dY;
	}

	public void fixCollisions() {
		for (GameObject dOb : this.ref.gameObjects) {
			if (dOb instanceof DynamicObject) {
				this.fixCollision((DynamicObject) dOb);
			}
		}
	}

	protected abstract void fixCollision(DynamicObject dOb);

	float lastX = 0;
	float lastY = 0;

	@Override
	public void update(List<Character> pressedKeys) {

		if (cScheme != null) {
			for(Character key: pressedKeys) {
				this.cScheme.doAction(key, this);
			}
			//			System.out.println(xVel + ", " + yVel);

		}

		//print position of other
//		else {
//			if (cScheme == null && (lastX != x || lastY != y)){
//				System.out.println(x + ", " + y);
//				System.out.println(lastX + "," + lastY);
//				lastX = x;
//				lastY = y;
//			}
//		}

		new Point(lastX, lastY).render(gApp);

		xVel += xAcc;
		yVel += yAcc;

		xVel *= this.ref.moveResistance;
		yVel *= this.ref.moveResistance;
		
		x += xVel;
		y += yVel;

		//Wrap around
		if (x > this.gApp.width) x = 0;
		else if (x < 0) x = this.gApp.width-1;

		if (y > this.gApp.height) y = 0;
		else if (y < 0) y = this.gApp.height-1;	

		this.fixCollisions();
	}
}
