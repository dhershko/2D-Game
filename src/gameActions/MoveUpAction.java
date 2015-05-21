package gameActions;

import gameObjects.DynamicObject;

public class MoveUpAction extends HoldAction {

	@Override
	public void doAction(DynamicObject toDoTo) {
		toDoTo.vel.y -= .3;
		
	}
	
	@Override
	public String toString() {
		return "MoveUpAction";
	}
	

}
