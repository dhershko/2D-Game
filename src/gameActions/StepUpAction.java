package gameActions;

import gameObjects.Sprite;
import geometryHelp.Vector;

public class StepUpAction extends HoldAction {


	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.translate(new Vector(0, -3));
	}
	
	@Override
	public String toString() {
		return "StepUpAction";
	}
	

}

