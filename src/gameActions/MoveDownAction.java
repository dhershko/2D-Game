package gameActions;

import gameObjects.DynamicObject;

public class MoveDownAction extends HoldAction {


	@Override
	public void doAction(DynamicObject toDoTo) {
		toDoTo.yVel += .3;
		
	}
	
	@Override
	public String toString() {
		return "MoveDownAction";
	}
	

}
