package gameActions;

import polygons.Shape;
import gameObjects.DynamicObject;

public class RotateCC extends HoldAction {

	@Override
	public void doAction(DynamicObject toDoTo) {
		Shape s = (Shape) toDoTo; 
		s.rotate(-.1);
	}

}
