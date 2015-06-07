package gameActions;

import gameObjects.GameObject;
import geometryHelp.Vector;

public class StepDownAction extends HoldAction {


	@Override
	public void doAction(GameObject toDoTo) {
		toDoTo.translate(new Vector(0, 3));
	}
	
	@Override
	public String toString() {
		return "StepDownAction";
	}
	

}

