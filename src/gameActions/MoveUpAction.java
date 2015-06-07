package gameActions;

import gameObjects.GameObject;

public class MoveUpAction extends HoldAction {

	@Override
	public void doAction(GameObject toDoTo) {
		toDoTo.vel.y -= .3;
		
	}
	
	@Override
	public String toString() {
		return "MoveUpAction";
	}
	

}
