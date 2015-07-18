package gameActions;

import menu.MenuHelpers;
import gameObjects.Sprite;

public class HomeMenuAction extends GameAction {

	@Override
	public void doAction(Sprite toDoTo) {
		toDoTo.ref.setNextRef(MenuHelpers.getTestMenu(toDoTo.ref));
	}

}
