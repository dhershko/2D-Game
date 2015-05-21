package gameObjects;

import java.util.HashMap;
import java.util.List;

import polygons.Point;
import polygons.Vector;
import gameActions.ControlScheme;
import gameActions.GameAction;
import gameReferee.GameApplet;
import gameReferee.Referee;

public abstract class DynamicObject extends GameObject{
	public Vector vel;
	public Point position;
	
//	public float x;
//	public float y;

	public ControlScheme cScheme;

	public double mass;
	public Vector impulse;
	public Vector force;

	public double restitution;


	public DynamicObject(Referee ref, GameApplet gApp, float x, float y, ControlScheme cScheme) {
		super(ref, gApp);

		this.cScheme = cScheme;

		this.impulse = new Vector(0,0);
		this.force = new Vector(0,0);
		
		//STUFF TO SET LATER
		this.mass = 1.0; 
		this.restitution = .91;
		this.position = new Point(x, y);
		this.vel = new Vector(0, 0);

	}	

	public void applyImpulse(Vector iImpulse) {
		this.impulse = impulse.add(iImpulse);
	}

	public void applyForce(Vector iForce) {
		this.force = this.force.add(iForce);
	}

	public void onTick(float t) {
		this.applyForce(this.ref.gravity);

		this.vel = this.vel.add(this.force.timesScalar(t));
		this.vel = this.vel.add(this.impulse.timesScalar(1.0/this.mass));
		this.translate(this.vel.timesScalar(t));

		this.vel = this.vel.timesScalar(this.ref.moveResistance);;

		this.impulse = new Vector(0,0);
		this.force = new Vector(0,0);
	}

	public double coefficentOfRestitution(DynamicObject other) {
		return Math.sqrt(this.restitution*other.restitution);
	}	
	protected void translate(Vector v) {
		this.position = this.position.add(v);
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

	public void destroyObject() {
		this.ref.gameObjects.remove(this);
	}
	
	public void fixNANS() {
		if (Double.isNaN(this.vel.x) || Double.isNaN(this.vel.y)){
			this.destroyObject();
		}
	}
	
	@Override
	public void update(List<Character> pressedKeys) {

		//Execute actions
		if (cScheme != null) {
			for(Character key: pressedKeys) {
				this.cScheme.doAction(key, this);
			}
		}

		//Perform physics updates
		this.onTick(1);

		//Wrap around
		if (this.position.x >= this.gApp.width)  {
			
			this.translate(new Vector(-(1+this.position.x), 0));
//			this.vel.x *= -1;
		}
		else if (this.position.x < 0) {
			this.translate(new Vector(-this.position.x + gApp.width-1, 0));
//			this.vel.x *= -1;
		}
		if (this.position.y >= this.gApp.height)  {
			
			this.translate(new Vector(0, -(this.position.y)));
//			this.vel.y *= -1;
		}
		else if (this.position.y < 0) {
			this.translate(new Vector(0, -this.position.y+ gApp.height-1));
//			this.vel.y *= -1;

		}

		//Fix collisions and update velocities
		this.fixCollisions();
	}
}
