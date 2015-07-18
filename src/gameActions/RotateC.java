package gameActions;

import shapes.Shape;
import gameObjects.Sprite;

public class RotateC extends HoldAction {

	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.rotationalVel += .01;
	}

}
