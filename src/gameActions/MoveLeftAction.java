package gameActions;

import gameObjects.DynamicObject;

public class MoveLeftAction extends HoldAction {


	@Override
	public void doAction(DynamicObject toDoTo) {
		toDoTo.xVel -= .3;
		
	}
	
	@Override
	public String toString() {
		return "MoveLeftAction";
	}
	

}
