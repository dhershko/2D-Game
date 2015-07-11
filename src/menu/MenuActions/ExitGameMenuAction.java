package menu.MenuActions;

import gameReferee.Referee;
import menu.MenuReferee;
import menus.AreYouSureMenu;
import menus.QuitReferee;

public class ExitGameMenuAction extends ChooseRefMenuAction{

	public ExitGameMenuAction(String displayName, MenuReferee menRef) {
		super(displayName, menRef, new AreYouSureMenu(new QuitReferee(), menRef));
	}
}
