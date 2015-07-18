package gameReferee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import menu.MenuHelpers;
import gameActions.GameAction;
import geometryHelp.Line;
import geometryHelp.Point;
import processing.core.PApplet;
import topLevel.Renderer;

public class GameApplet extends PAppletRenderer implements Renderer  {
	public List<GameAction> actionsToTake;
	private Referee controllingRef;
	private  HashMap<Character, Boolean> keysInUse;

	public Referee getInitialRef() {
		Referee ref = MenuHelpers.getTestMenu(this);
		return ref;
	}
	
	public Referee getControllingRef() {
		if (this.controllingRef == null) {
			this.controllingRef = getInitialRef();
			return this.controllingRef;
		}
		return this.controllingRef;
	}
	
	public List<Character> getKeysInUse() {
		List<Character> toReturn = new ArrayList<Character>();
		for (Character usedChar : keysInUse.keySet()) {
			if (this.keysInUse.get(usedChar)) toReturn.add(usedChar);
		}
		return toReturn;
	}
	
	
	public void keyPressed() {
		if (key == ESC) key='\t';
		this.keysInUse.put(key, true);
	}
	
	public void keyReleased() {
		this.keysInUse.put(key, false);
	}
	
	public void setup() {
		size(640, 360, P2D);
		this.keysInUse = new HashMap<Character, Boolean>();
		background(0);
		this.noFill();
	}

	public void draw() { 
		background(255);
		Referee nextRef = this.getControllingRef().getNextRef();
		this.controllingRef.setNextRef(this.controllingRef);
		this.controllingRef = nextRef;
		this.getControllingRef().timeStep(this);
		this.getControllingRef().render(this);
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
	
	public void ellipse(double a, double b, double c, double d) {
		super.ellipse((float)a, (float)b, (float)c, (float)d);
		
	}

	public void runGame() {
		PApplet.main(new String[] { "--present", GameApplet.class.getName() });
	}

}