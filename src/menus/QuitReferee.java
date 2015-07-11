package menus;

import gameReferee.GameApplet;
import menu.MenuReferee;

public class QuitReferee extends MenuReferee{
	@Override
	public void timeStep(GameApplet gApp) {
		System.exit(0);
		
	}

}
