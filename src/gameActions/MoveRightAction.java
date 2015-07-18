package gameActions;

import gameObjects.Sprite;

public class MoveRightAction extends HoldAction {




	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.vel.x += .3;
	}
	
	@Override
	public String toString() {
		return "MoveRightAction";
	}
}
