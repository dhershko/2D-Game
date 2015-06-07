package gameActions;

import gameObjects.GameObject;

import java.util.HashMap;

public class ControlScheme {
	HashMap<Character, GameAction> actionMap;
	GameObject toDoTo;
	
	public ControlScheme(HashMap<Character, GameAction> actionMap) {
		this.actionMap = actionMap;
	}
	
	public void doAction(char input, GameObject toDoTo) {
		GameAction toDo = this.actionMap.get(input);
		if (toDo != null) {
			toDo.doAction(toDoTo);
		}
	}
}
