package gameReferee;


import gameActions.GameAction;
import gameActions.GameActionHelpers;
import gameObjects.DynamicObject;
import gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

import polygons.Point;
import polygons.Polygon;
import polygons.Shape;
import polygons.Vector;
import processing.core.PApplet;

public class Referee {
	//public StaticObject [][] gameGrid;
	public List<GameObject> gameObjects;
	public List<GameAction> gameActionsToExecute;
	public float moveResistance = (float) .99;

	public Vector gravity;

	public GameApplet procApp;

	public Referee(GameApplet gApp) {
		this.procApp = gApp;
		this.gameObjects= new ArrayList<GameObject>();
		this.gameActionsToExecute = new ArrayList<GameAction>();

		this.gravity = new Vector(0, 0);

		this.initializeMap();
	}

	public void render() {
		for (GameObject dyOb : this.gameObjects) {
			dyOb.render(procApp);
		}
	}

	public void addGameObject(GameObject toAdd) {
		gameObjects.add(toAdd);
	}

	public Vector averageObjectVelocity() {
		Vector toReturn = new Vector(0 ,0);
		for (GameObject dOb : this.gameObjects) {
			Shape s =  (Shape) dOb;
			toReturn = toReturn.add(s.vel);
		}
		return toReturn.timesScalar(1.0/this.gameObjects.size());
	}

	public double averageObjectSpeed() {
		double toReturn = 0;
		for (GameObject dOb : this.gameObjects) {
			Shape s =  (Shape) dOb;
			toReturn += s.vel.getLength();
		}
		return toReturn/this.gameObjects.size();
	}

	public void initializeMap() {
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(10, 50));
		//		points.add(new Point(-50,50));

		points.add(new Point(55, -50));
		points.add(new Point(-30, -55));
		points.add(new Point(-50, 5));

		double randomVelMag = 5;

		addGameObject(new Polygon(this, procApp,GameActionHelpers.getWASDMovementScheme(), 0, 0,  5, 20));
		for(int i = 0; i < 50; i++) {
			Polygon toAdd = new Polygon(this, procApp,null, procApp.random(procApp.width), procApp.random(procApp.height),  5, 20);

			double randomTheta = this.procApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);

		}

	}

	Vector lastAverageVel = new Vector(0,0);

	void timeStep() {
		this.procApp.stroke(1, 255, 1);
		List<Character> pressedKeys = this.procApp.getKeysInUse();
		for (GameObject dyOb: gameObjects) {
			dyOb.update(pressedKeys);
		}
//		Vector currAvgVel = this.averageObjectVelocity();
//		if (!lastAverageVel.equals(currAvgVel)) {
//			System.out.println(currAvgVel.getLength());
//		}
//		lastAverageVel = currAvgVel;
	}


	/*
	 * TODO:
	 * points
	 * lines
	 * vectors?
	 * circles
	 * ellipses
	 * segments
	 * concave polygons
	 * effects
	 * 
	 * 
	 * Fix speed overflow bug
	 * 
	 */
}
