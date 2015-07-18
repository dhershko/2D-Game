package gameActions;

import gameObjects.Sprite;

import java.util.HashMap;

public class ControlScheme {
	HashMap<Character, GameAction> actionMap;
	Sprite toDoTo;
	
	public ControlScheme(HashMap<Character, GameAction> actionMap) {
		this.actionMap = actionMap;
	}
	
	public void doAction(char input, Sprite toDoTo) {
		GameAction toDo = this.actionMap.get(input);
		if (toDo != null) {
			toDo.doAction(toDoTo);
		}
	}
}
