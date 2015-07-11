package menus;

import gameReferee.Referee;
import menu.MenuReferee;
import menu.MenuActions.ChooseRefMenuAction;
import menu.MenuActions.MenuAction;

public class AreYouSureMenu extends MenuReferee {
	public AreYouSureMenu(Referee yes, Referee no) {
		new ChooseRefMenuAction("no", this, no);
		new ChooseRefMenuAction("yes", this, yes);
	}


}
