package gameActions;

import gameObjects.DynamicObject;

public class MoveRightAction extends HoldAction {




	@Override
	public void doAction(DynamicObject toDoTo) {
		toDoTo.xVel += .3;
	}
	
	@Override
	public String toString() {
		return "MoveRightAction";
	}
}
