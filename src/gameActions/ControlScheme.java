package gameActions;

import gameObjects.DynamicObject;

import java.util.HashMap;

public class ControlScheme {
	HashMap<Character, GameAction> actionMap;
	DynamicObject toDoTo;
	
	public ControlScheme(HashMap<Character, GameAction> actionMap) {
		this.actionMap = actionMap;
	}
	
	public void doAction(char input, DynamicObject toDoTo) {
		GameAction toDo = this.actionMap.get(input);
		if (toDo != null) toDo.doAction(toDoTo);
	}
}
