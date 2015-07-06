package gameReferee;


import gameActions.ControlScheme;
import gameActions.GameAction;
import gameActions.GameActionHelpers;
import gameObjects.GameObject;
import gameObjects.GameObject;
import geometryHelp.Point;
import geometryHelp.Vector;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import shapes.Circle;
import shapes.LineShape;
import shapes.Polygon;
import shapes.Shape;
import shapes.ShapePoint;

public class Referee {
	//public StaticObject [][] gameGrid;
	public List<GameObject> gameObjects;
	public List<GameAction> gameActionsToExecute;
	public float moveResistance = (float) 1;

	public boolean paused;

	public Vector gravity;

	public GameApplet procApp;

	public Referee(GameApplet gApp) {
		this.procApp = gApp;
		this.gameObjects= new ArrayList<GameObject>();
		this.gameActionsToExecute = new ArrayList<GameAction>();
		this.paused = false;
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

		double randomVelMag = .1;


		// Generic polygons
		for(int i = 0; i < 15; i++) {
			Polygon toAdd = new Polygon(this, procApp,null, procApp.random(procApp.width), procApp.random(procApp.height),  5, 20);
			double randomTheta = this.procApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}

		// Controled polygons
		for(int i = 0; i < 1; i++) {
			Polygon toAdd = new Polygon(this, procApp,GameActionHelpers.getWASDMovementScheme(), procApp.random(procApp.width), procApp.random(procApp.height),  5, 20);
			double randomTheta = this.procApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}
		// Controlled Points
		for(int i = 0; i < 20; i++) {
			ShapePoint toAdd = new ShapePoint(this, procApp, procApp.random(procApp.width), procApp.random(procApp.height), GameActionHelpers.getWASDMovementScheme());
			double randomTheta = this.procApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}

		// Lines
		for(int i = 0; i < 0; i++) {
			double lineLength = 100;
			Point p1 = new Point(procApp.random(this.procApp.width), procApp.random(this.procApp.height));
			double randomTheta1 = this.procApp.random((float)Math.PI*2);

			Point p2 = new Point(p1.x + Math.cos(randomTheta1)*lineLength, p1.y + Math.sin(randomTheta1)*lineLength);

			LineShape toAdd = new LineShape(this, procApp, GameActionHelpers.getWASDMovementScheme(), p1, p2);

			double randomTheta = this.procApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}

		// Circles
		for(int i = 0; i < 15; i++) {
			double radius = 20;
			Circle toAdd = new Circle(this, procApp,procApp.random(procApp.width), procApp.random(procApp.height), radius, null);
			addGameObject(toAdd);
		}

		// Big square
		List<Point> big = new ArrayList<Point>() {{
			add(new Point(200 + 100, 200 + 100));
			add(new Point(200 + 100, 200 - 100));
			add(new Point(200 - 100, 200 - 100));
			add(new Point(200 - 100, 200 + 100));
		}};
		Polygon bigP = new Polygon(this, procApp, null, 200, 200, big);
		//addGameObject(bigP);	

	}

	Vector lastAverageVel = new Vector(0,0);

	void timeStep() {
		if (!this.paused) {
			this.procApp.stroke(1, 255, 1);
			List<Character> pressedKeys = this.procApp.getKeysInUse();
			for (GameObject dyOb: gameObjects) {
				dyOb.update(pressedKeys);
			}
		}
	}


	/*
	 * TODO:
	 * Ellipses
	 * Segments
	 * Concave polygons
	 * Compound shapes
	 * Effects
	 * 
	 * 
	 * Fix speed overflow bug
	 * 
	 */
}
