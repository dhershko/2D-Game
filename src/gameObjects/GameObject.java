package gameObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shapes.Impulse;
import gameActions.ControlScheme;
import gameActions.GameAction;
import gameReferee.GameApplet;
import gameReferee.Referee;
import geometryHelp.Point;
import geometryHelp.Vector;

public abstract class GameObject{
	public Vector vel;
	public Point position;

	public ControlScheme cScheme;

	public double mass;
	public List<Impulse> impulses;
	public Vector force;

	public double rotationalVel;

	public double restitution;
	
	public double termVel;
	public double rotTermVel;
	
	public Referee ref;
	public GameApplet gApp;
	
	public GameObject(Referee ref, GameApplet gApp, double x, double y, ControlScheme cScheme) {
		this.ref = ref;
		this.gApp = gApp;
		this.cScheme = cScheme;

		this.impulses = new ArrayList<Impulse>();
		this.force = new Vector(0,0);

		//STUFF TO SET LATER
		this.restitution = 1;
		this.position = new Point(x, y);
		this.vel = new Vector(0, 0);
		this.termVel = 3;
		this.rotTermVel = .1;
		this.mass = 1;
	}	

	public void applyImpulse(Impulse iImpulse) {
		this.impulses.add(iImpulse);
	
	}

	public void applyForce(Vector iForce) {
		this.force = this.force.add(iForce);
	}

	public void onTick(float t) {
		this.applyForce(this.ref.gravity);

		this.vel = this.vel.add(this.force.timesScalar(t));
		
		for (Impulse impulse : this.impulses) {
			impulse.executeImpulse();
		}
		
		this.rotate(this.rotationalVel*t);
		this.translate(this.vel.timesScalar(t));

		//Terminal velocity
		if (this.vel.getLength() > this.termVel) {
			this.vel.setLength(this.termVel);
		}
		if (Math.abs(this.rotationalVel) > this.rotTermVel) {
			this.rotationalVel = this.rotTermVel*Math.signum(this.rotationalVel);
		}

		this.vel = this.vel.timesScalar(this.ref.moveResistance);
		this.rotationalVel *= this.ref.moveResistance;
		
		
		this.impulses = new ArrayList<Impulse>();
		this.force = new Vector(0,0);
	}

	public double coefficentOfRestitution(GameObject other) {
		return Math.sqrt(this.restitution*other.restitution);
	}	
	

	public void translate(Vector v) {
		this.position = this.position.add(v);
	}
	protected void rotate(double v) {
	}

	public void fixCollisions() {
		for (GameObject dOb : this.ref.gameObjects) {
			if (dOb instanceof GameObject) {
				this.fixCollision((GameObject) dOb);
			}
		}
	}

	protected abstract void fixCollision(GameObject dOb);

	double lastX = 0;
	double lastY = 0;

	public void destroyObject() {
		this.ref.gameObjects.remove(this);
	}
	
	public void beyondMapUpdate() {
		if (this.position.x >= this.gApp.width)  {

			this.translate(new Vector(-(this.position.x), 0));
		}
		else if (this.position.x < 0) {
			this.translate(new Vector(-this.position.x + gApp.width-1, 0));
		}
		if (this.position.y >= this.gApp.height)  {

			this.translate(new Vector(0, -(this.position.y)));
		}
		else if (this.position.y < 0) {
			this.translate(new Vector(0, -this.position.y+ gApp.height-1));
		}
	}

	public abstract void render(GameApplet gApp);
	
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
		this.beyondMapUpdate();

		//Fix collisions and update velocities
		this.fixCollisions();
		
	}
}
