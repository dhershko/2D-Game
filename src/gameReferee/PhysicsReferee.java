package gameReferee;


import gameActions.GameAction;
import gameActions.GameActionHelpers;
import gameObjects.GameObject;
import geometryHelp.Point;
import geometryHelp.Vector;

import java.util.ArrayList;
import java.util.List;

import shapes.Circle;
import shapes.LineShape;
import shapes.Polygon;
import shapes.Shape;
import shapes.ShapePoint;

public class PhysicsReferee extends Referee {
	public List<GameObject> gameObjects;
	public List<GameAction> gameActionsToExecute;
	public float moveResistance = (float) 1;

	public boolean paused;

	public Vector gravity;

	public PhysicsReferee() {
		this.gameObjects= new ArrayList<GameObject>();
		this.gameActionsToExecute = new ArrayList<GameAction>();
		this.paused = false;
		this.gravity = new Vector(0, 0);
	}

	@Override
	public void render(GameApplet gApp) {
		for (GameObject dyOb : this.gameObjects) {
			dyOb.render(gApp);
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

	public void initializeMap(GameApplet gApp) {
		System.out.println("Initializing PhysicsReferee map...");
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(10, 50));
		//		points.add(new Point(-50,50));

		points.add(new Point(55, -50));
		points.add(new Point(-30, -55));
		points.add(new Point(-50, 5));

		double randomVelMag = .1;


		// Generic polygons
		for(int i = 0; i < 20; i++) {
			Polygon toAdd = new Polygon(this, gApp,null, gApp.random(gApp.width), gApp.random(gApp.height),  5, 20);
			double randomTheta = gApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}

		// Controled polygons
		for(int i = 0; i < 1; i++) {
			Polygon toAdd = new Polygon(this, gApp,GameActionHelpers.getWASDMovementScheme(), gApp.random(gApp.width), gApp.random(gApp.height),  5, 20);
			double randomTheta = gApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}
		// Controlled Points
		for(int i = 0; i < 0; i++) {
			ShapePoint toAdd = new ShapePoint(this, gApp, gApp.random(gApp.width), gApp.random(gApp.height), GameActionHelpers.getWASDMovementScheme());
			double randomTheta = gApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}
		// Points
		for(int i = 0; i < 0; i++) {
			ShapePoint toAdd = new ShapePoint(this, gApp, 430, 150 , null);
			double randomTheta = gApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}

		// Lines
		for(int i = 0; i < 0; i++) {
			double lineLength = 100;
			Point p1 = new Point(gApp.random(gApp.width), gApp.random(gApp.height));
			double randomTheta1 = gApp.random((float)Math.PI*2);

			Point p2 = new Point(p1.x + Math.cos(randomTheta1)*lineLength, p1.y + Math.sin(randomTheta1)*lineLength);

			LineShape toAdd = new LineShape(this, gApp, GameActionHelpers.getWASDMovementScheme(), p1, p2);

			double randomTheta = gApp.random((float)Math.PI*2);
			toAdd.vel.x = randomVelMag*Math.cos(randomTheta);
			toAdd.vel.y = randomVelMag*Math.sin(randomTheta);
			addGameObject(toAdd);
		}

		// Circles
		for(int i = 0; i < 0; i++) {
			double radius = 20;
			Circle toAdd = new Circle(this, gApp,gApp.random(gApp.width), gApp.random(gApp.height), radius, null);
			addGameObject(toAdd);
		}

		// Big square
		List<Point> big = new ArrayList<Point>() {{
			add(new Point(200 + 100, 200 + 100));
			add(new Point(200 + 100, 200 - 100));
			add(new Point(200 - 100, 200 - 100));
			add(new Point(200 - 100, 200 + 100));
		}};
		Polygon bigP = new Polygon(this, gApp, null, 200, 200, big);
		//addGameObject(bigP);	

	}

	Vector lastAverageVel = new Vector(0,0);

	@Override
	public void timeStep(GameApplet gApp) {
		List<Character> pressedKeys = gApp.getKeysInUse();
		if (!this.paused) {
			gApp.stroke(1, 255, 1);
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
	 * Section off Processing app logic
	 *  
	 * Fix speed overflow bug -- a result of doubles
	 * 
	 */
}
