package gameActions;

import gameObjects.Sprite;

public class MoveDownAction extends HoldAction {


	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.vel.y += .3;
	}
	
	@Override
	public String toString() {
		return "MoveDownAction";
	}
	

}
