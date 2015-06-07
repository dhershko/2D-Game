package gameActions;

import shapes.Shape;
import gameObjects.GameObject;

public class RotateCC extends HoldAction {

	@Override
	public void doAction(GameObject toDoTo) {
		Shape s = (Shape) toDoTo; 
		s.rotationalVel += -.01;
	}

}
