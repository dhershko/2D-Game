package gameReferee;


import gameObjects.Sprite;
import geometryHelp.Point;
import geometryHelp.Vector;

import java.util.ArrayList;
import java.util.List;

import DefaultHelpers.SpriteRefHelpers;
import EventHandling.EventHandler;
import EventHandling.EventsContainer;
import EventHandling.events.ActionEvent;
import EventHandling.events.CollisionEvent;
import EventHandling.events.Event;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;
import shapes.Circle;
import shapes.LineShape;
import shapes.Polygon;
import shapes.Shape;
import shapes.ShapePoint;
import topLevel.Renderer;

public class SpriteReferee extends Referee {
	public List<Sprite> sprites;
	public float moveResistance = (float) 1;

	public double sceneHeight = 300;
	public double sceneWidth = 600;

	public boolean paused;

	public Vector gravity;

	private EventHandler cEventHandler;

	public SpriteReferee(EventHandler cEventHandler) {
		this.sprites= new ArrayList<Sprite>();
		this.paused = false;
		this.gravity = new Vector(0, 0);
		SpriteRefHelpers.getExample(this);
		this.cEventHandler = cEventHandler;
	}

	public List<Sprite> getPossibleCollidingSprites(Sprite toCheckFor) {
		return this.sprites;
	}


	private void handleEvent(CollisionEvent event) {
	}


	//	public Vector averageObjectVelocity() {
	//		Vector toReturn = new Vector(0 ,0);
	//		for (Sprite dOb : this.sprites) {
	//			toReturn = toReturn.add(dOb.vel);
	//		}
	//		return toReturn.timesScalar(1.0/this.sprites.size());
	//	}
	//
	//	public double averageObjectSpeed() {
	//		double toReturn = 0;
	//		for (Sprite dOb : this.sprites) {
	//			toReturn += dOb.vel.getLength();
	//		}
	//		return toReturn/this.sprites.size();
	//	}



	@Override
	public void timeStep(GameApplet gApp) {
		List<Character> pressedKeys = gApp.getKeysInUse();
		if (!this.paused) {
			for (Sprite sprite: sprites) {
				EventsContainer events = sprite.update(pressedKeys);

				// First index is collision events.
				for (CollisionEvent event : events.getCollisionEvents()) {
					this.cEventHandler.handleCollisionEvent(event);
				}
				// Second index is beyond scene events.
				for (BeyondSceneEvent event : events.getBeyondSceneEvents()) {
					this.cEventHandler.handleOffSceneEvent(event);
				}
				// Third index is action events.
				for (ActionEvent event : events.getActionEvents()) {
					this.cEventHandler.handleActionEvent(event);
				}
			}
		}
	}	

	@Override
	public void render(Renderer gApp) {
		for (Sprite dyOb : this.sprites) {
			dyOb.render(gApp);
		}
	}


	/*
	 * TODO:
	 * Ellipses
	 * Segments
	 * Concave polygons
	 * Compound shapes
	 * Effects
	 * Section off Processing app logic
	 *  
	 * Fix speed overflow bug -- a result of doubles
	 * 
	 */
}
