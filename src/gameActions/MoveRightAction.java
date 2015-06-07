package gameActions;

import gameObjects.GameObject;

public class MoveRightAction extends HoldAction {




	@Override
	public void doAction(GameObject toDoTo) {
		toDoTo.vel.x += .3;
	}
	
	@Override
	public String toString() {
		return "MoveRightAction";
	}
}
