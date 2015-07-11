package gameActions;

import menu.MenuHelpers;
import gameObjects.GameObject;

public class HomeMenuAction extends GameAction {

	@Override
	public void doAction(GameObject toDoTo) {
		toDoTo.ref.setNextRef(MenuHelpers.getTestMenu(toDoTo.gApp, toDoTo.ref));
	}

}
