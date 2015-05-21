package gameActions;

import gameObjects.DynamicObject;

public class MoveLeftAction extends HoldAction {


	@Override
	public void doAction(DynamicObject toDoTo) {
		toDoTo.vel.x -= .3;
		
	}
	
	@Override
	public String toString() {
		return "MoveLeftAction";
	}
	

}
