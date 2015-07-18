package menu;

import DefaultHelpers.DefaultCollisionEventHandler;
import menu.MenuActions.ChooseRefMenuAction;
import menu.MenuActions.ExitGameMenuAction;
import gameReferee.GameApplet;
import gameReferee.Referee;
import gameReferee.SpriteReferee;

public class MenuHelpers {
	public static Referee getTestMenu(GameApplet gApp) {
		MenuReferee toReturn = new MenuReferee();
		SpriteReferee ref = new SpriteReferee(new DefaultCollisionEventHandler());
		new ChooseRefMenuAction("Physics", toReturn, ref);
		new ExitGameMenuAction("Quit", toReturn);
		return ref;
	}

}