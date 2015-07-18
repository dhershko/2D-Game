package gameActions;

import gameObjects.Sprite;
import geometryHelp.Vector;

public class StepRightAction extends HoldAction {


	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.translate(new Vector(3, 0));
	}
	
	@Override
	public String toString() {
		return "StepRightAction";
	}
	

}

