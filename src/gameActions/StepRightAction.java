package gameActions;

import gameObjects.GameObject;
import geometryHelp.Vector;

public class StepRightAction extends HoldAction {


	@Override
	public void doAction(GameObject toDoTo) {
		toDoTo.translate(new Vector(3, 0));
	}
	
	@Override
	public String toString() {
		return "StepRightAction";
	}
	

}

