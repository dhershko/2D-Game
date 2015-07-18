package gameActions;

import gameObjects.Sprite;

public class MoveLeftAction extends HoldAction {


	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.vel.x -= .3;
		
	}
	
	@Override
	public String toString() {
		return "MoveLeftAction";
	}
	

}
