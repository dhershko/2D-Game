package menu;

import menu.MenuActions.ChooseRefMenuAction;
import menu.MenuActions.ExitGameMenuAction;
import gameReferee.GameApplet;
import gameReferee.PhysicsReferee;
import gameReferee.Referee;

public class MenuHelpers {
	public static Referee getTestMenu(GameApplet gApp) {
		MenuReferee toReturn = new MenuReferee();

		PhysicsReferee ref = new PhysicsReferee();
		ref.initializeMap(gApp);
		new ChooseRefMenuAction("Physics", toReturn, ref);
		new ExitGameMenuAction("Quit", toReturn);
		return toReturn;
	}
	public static Referee getTestMenu(GameApplet gApp, PhysicsReferee physics) {
		MenuReferee toReturn = new MenuReferee();
		new ChooseRefMenuAction("Physics", toReturn, physics);

		new ExitGameMenuAction("Quit", toReturn);
		return toReturn;
	}
}