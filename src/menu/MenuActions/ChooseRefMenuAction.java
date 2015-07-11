package menu.MenuActions;

import menu.MenuReferee;
import gameReferee.Referee;

public class ChooseRefMenuAction extends MenuAction {
	Referee toChoose;

	/**
	 * 
	 * @param displayName
	 * @param menRef
	 * @param chosenRef 
	 */
	public ChooseRefMenuAction(String displayName, MenuReferee menRef, Referee chosenRef) {
		super(displayName, menRef);
		this.toChoose = chosenRef;
	}

	@Override
	public void doAction() {
		this.menRef.setNextRef(this.toChoose);
	}

	@Override
	public String toString() {
		return "ChooseRefMenuAction";
	}
}

