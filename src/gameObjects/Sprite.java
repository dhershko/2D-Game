package gameObjects;

import java.util.ArrayList;
import java.util.List;

import DefaultHelpers.DefaultCollisionEventHandler;
import EventHandling.EventsContainer;
import EventHandling.events.ActionEvent;
import EventHandling.events.CollisionEvent;
import EventHandling.events.Event;
import EventHandling.events.beyondSceneEvents.BeyondBotEvent;
import EventHandling.events.beyondSceneEvents.BeyondLeftEvent;
import EventHandling.events.beyondSceneEvents.BeyondRightEvent;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;
import EventHandling.events.beyondSceneEvents.BeyondTopEvent;
import shapes.Impulse;
import shapes.Shape;
import topLevel.Renderer;
import gameReferee.GameApplet;
import gameReferee.SpriteReferee;
import geometryHelp.Point;
import geometryHelp.Vector;

public class Sprite{
	public Vector vel;
	public double rotationalVel;

	public List<Impulse> impulses;
	public Vector force;
	public double restitution;

	public double termVel;
	public double rotTermVel;
	
	public double rotation;
	
	public Shape hitBox;
	public SpriteReferee ref;

	private String propertiesConc;
	
	public Sprite(String[] properties, Shape hitBox, SpriteReferee ref) {
		this.propertiesConc = "";
		for (String prop : properties) {
			this.propertiesConc += prop + DefaultCollisionEventHandler.KEYWORDDELINEATOR;
		}
		this.propertiesConc.substring(0, this.propertiesConc.length()-1);
		this.hitBox = hitBox;
		this.ref = ref;

		this.impulses = new ArrayList<Impulse>();
		this.force = new Vector(0,0);

		//STUFF TO SET LATER
		this.restitution = .5;
		this.vel = new Vector(0, 0);
		this.termVel = 10;
		this.rotTermVel = .5;
		ref.sprites.add(this);
		this.rotation = 0;
	}	

	public void applyImpulse(Impulse iImpulse) {
		this.impulses.add(iImpulse);
	}

	public void applyForce(Vector iForce) {
		this.force = this.force.add(iForce);
	}

	public void performTimeUpdate(float t) {
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
	
	private List<CollisionEvent> getCollisionRelevantEvents() {
		List<CollisionEvent> toReturn = new ArrayList<CollisionEvent>();
		
		// Check colliding with other sprite
		List<Sprite> toCheckFor = this.ref.getPossibleCollidingSprites(this);
		for (Sprite maybeCollidingWith : toCheckFor) {
			Vector MTV = this.hitBox.collisionMTV(maybeCollidingWith.hitBox);
			if (MTV != null) {
				toReturn.add(new CollisionEvent(this, maybeCollidingWith, MTV));
			}
		}

		return toReturn;
	}
	
	
	public List<CollisionEvent> onTick(float t) {
		this.performTimeUpdate(t);
		return this.getCollisionRelevantEvents();
	}

	public double coefficentOfRestitution(Sprite other) {
		return Math.sqrt(this.restitution*other.restitution);
	}	
	

	public void translate(Vector v) {
		this.hitBox.translate(v);
	}
	protected void rotate(double v) {
		this.hitBox.rotate(v);
		this.rotation += v % (Math.PI*2);
	}
	
	public boolean isCollidingWith(Sprite other) {
		return this.hitBox.isCollidingWith(other.hitBox);
	}
	
	private List<BeyondSceneEvent> getBeyondSceneEvents() {
		List<BeyondSceneEvent> toReturn = new ArrayList<BeyondSceneEvent>();
		if (this.hitBox.position.x >= this.ref.sceneWidth)  {
			toReturn.add(new BeyondRightEvent(this));
		}
		else if (this.hitBox.position.x < 0) {
			toReturn.add(new BeyondLeftEvent(this));
		}
		if (this.hitBox.position.y >= this.ref.sceneHeight)  {
			toReturn.add(new BeyondBotEvent(this));
		}
		else if (this.hitBox.position.y < 0) {
			toReturn.add(new BeyondTopEvent(this));
		}
		
		return toReturn;
//		if (this.position.x >= this.sceneWidth)  {
//			this.translate(new Vector(-(this.position.x), 0));
//		}
//		else if (this.position.x < 0) {
//			this.translate(new Vector(-this.position.x + this.sceneWidth-1, 0));
//		}
//		if (this.position.y >= this.sceneHeight)  {
//
//			this.translate(new Vector(0, -(this.position.y)));
//		}
//		else if (this.position.y < 0) {
//			this.translate(new Vector(0, -this.position.y+ this.sceneHeight-1));
//		}
	}

	public void render(Renderer rend) {
		this.hitBox.render(rend);
	}
	
	/**
	 * 
	 * @param pressedKeys
	 * @return first index is collision events, second is beyond map events
	 */
	public EventsContainer update(List<Character> pressedKeys) {
		EventsContainer toReturn = new EventsContainer();

		//Perform physics updates
		List<CollisionEvent> collisionEvents  = this.onTick(1);
		toReturn.addCollisionEvents(collisionEvents);
		
		//Get beyond scene events
		List<BeyondSceneEvent> beyondSceneEvents = this.getBeyondSceneEvents();
		toReturn.addBeyondSceneEvents(beyondSceneEvents);
		//Manually add action event
		toReturn.addActionEvent(new ActionEvent(this, pressedKeys));

		return toReturn;
	}
	
	public String getProperties() {
		return this.propertiesConc;
	}
	
	@Override 
	public String toString() {
		return "Sprite with properties " + this.propertiesConc +" and hitbox " + this.hitBox.toString();
	}
}
