package gameReferee;


import gameActions.GameAction;
import gameActions.GameActionHelpers;
import gameObjects.DynamicObject;
import gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

import polygons.Point;
import polygons.Polygon;
import processing.core.PApplet;

public class Referee {
	//public StaticObject [][] gameGrid;
	public List<GameObject> gameObjects;
	public List<GameAction> gameActionsToExecute;
	public float gravity = -1;
	public float moveResistance = (float) .9;

	public GameApplet procApp;

	public Referee(GameApplet gApp) {
		this.procApp = gApp;
		this.gameObjects= new ArrayList<GameObject>();
		this.gameActionsToExecute = new ArrayList<GameAction>();

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
	
	public void initializeMap() {
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(10, 50));
		//		points.add(new Point(-50,50));

		points.add(new Point(55, -50));
		points.add(new Point(-30, -55));
		points.add(new Point(-50, 5));

		
		addGameObject(new Polygon(this, procApp,GameActionHelpers.getWASDMovementScheme(), 0, 0,  5, 50));
		for(int i = 0; i < 20; i++)
		addGameObject(new Polygon(this, procApp,null, procApp.random(procApp.width), procApp.random(procApp.height),  5, 50));
//		addGameObject(new Polygon(this, procApp,null, procApp.random(procApp.width), procApp.random(procApp.height),  20, 50));
//		addGameObject(new Polygon(this, procApp,null, procApp.random(procApp.width), procApp.random(procApp.height),  20, 50));

		//		this.players.add(toAdd);
		//		
		//		List<Point> points2 = new ArrayList<Point>();
		//		points2.add(new Point(-10, 50));
		//		points2.add(new Point(-50,60));
		//
		//		points2.add(new Point(-50,-50));
		//		points2.add(new Point(10,-100));
		//		Polygon toAdd2 = new Polygon(this, procApp, 300, 200, points);
		//		
		//		dynamicObjects.add(toAdd2);
		//		this.players.add(toAdd2);
	}

	void timeStep() {
		this.procApp.stroke(1, 255, 1);
		
		for (GameObject dyOb: gameObjects) {
			dyOb.update(this.procApp.pressedKeys);
		}
	}


}
