package gameReferee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gameActions.GameAction;
import gameActions.GameActionHelpers;
import processing.core.PApplet;

public class GameApplet extends PApplet {
	Referee gameRef;
	public List<GameAction> actionsToTake;
	
	private  HashMap<Character, Boolean> keysInUse;
	
	public GameApplet() {
		this.keysInUse = new HashMap<Character, Boolean>();
	}
	
	public List<Character> getKeysInUse() {
		List<Character> toReturn = new ArrayList<Character>();
		for (Character usedChar : keysInUse.keySet()) {
			if (this.keysInUse.get(usedChar)) toReturn.add(usedChar);
		}
		return toReturn;
	}
	
	public void keyPressed() {
		this.keysInUse.put(key, true);
	}
	
	public void keyReleased() {
		this.keysInUse.put(key, false);
	}

	public void setup() {
		size(640, 360);
		background(0);
		this.gameRef = new Referee(this);
		this.noFill();
	}

	public void draw() { 
		background(255);
		gameRef.timeStep();
		gameRef.render();
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", GameApplet.class.getName() });

	} 

	public void line(double x1, double y1, double x2, double y2) {
		super.line((float)x1, (float)y1, (float)x2, (float)y2);		
	}
	
	public void vertex(double x, double y) {
		super.vertex((float)x,(float)y);
	}
	public void point(double x, double y) {
		super.point((float)x,(float)y);
	}	
	
	public void translate(double xOffset, double yOffset) {
		super.translate((float)xOffset, (float)yOffset);
	}
	
	public void rect(double a, double b, double c, double d) {
		super.rect((float)a, (float)b, (float)c, (float)d);
	}
}