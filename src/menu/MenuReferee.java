package menu;

import gameReferee.GameApplet;
import gameReferee.Referee;

import java.util.ArrayList;
import java.util.List;

import menu.MenuActions.MenuAction;

public class MenuReferee extends Referee {
	private int selectedActionIndex;
	List<Character> pressedLastTime;
	private ArrayList<MenuAction> menuActions;

	public MenuReferee() {
		this.selectedActionIndex = 0;
		this.menuActions = new ArrayList<MenuAction>();
		this.pressedLastTime = new ArrayList<Character>();
	}
	
	public void AddAction(MenuAction menuAction) {
		// Formerly empty
		if (this.menuActions.isEmpty()) {
			menuAction.isSelected = true;
		}
		menuAction.setMenRef(this);
		this.menuActions.add(menuAction);
	}

	
	private void handlePressDown() {
		menuActions.get(selectedActionIndex).isSelected = false;
		selectedActionIndex += 1;
		selectedActionIndex = selectedActionIndex % menuActions.size();
		menuActions.get(selectedActionIndex).isSelected = true;
	}
	
	private void handlePressUp() {
		menuActions.get(selectedActionIndex).isSelected = false;
		selectedActionIndex -= 1;
		if (selectedActionIndex < 0) selectedActionIndex += menuActions.size();
		menuActions.get(selectedActionIndex).isSelected = true;
	}
	
	private void handleSelect() {
		menuActions.get(selectedActionIndex).doAction();
	}

	public List<Character> getUnpressedActions(List<Character> pressedKeys) {
		List<Character> toReturn = new ArrayList<Character>();
		for(Character currChar : this.pressedLastTime) {
			if (!pressedKeys.contains(currChar)) {
				toReturn.add(currChar);
			}
		}
		return toReturn;
	}

	
	
	@Override
	public void render(GameApplet gApp) {
		int textHeight = 30;
		for (int i = 0; i <menuActions.size(); i++) {
			MenuAction currAction = menuActions.get(i);
			currAction.render(gApp, (i+5)*textHeight, textHeight);
		}
	}

	@Override
	public void timeStep(GameApplet gApp) {
		List<Character> pressedKeys = gApp.getKeysInUse();
		List<Character> unpressedKeys = this.getUnpressedActions(pressedKeys);
		for (Character currChar : unpressedKeys) {
			switch (currChar) {
			case 'w':
				handlePressUp();
				break;
			case 's':
				handlePressDown();
				break;
			case '\n':
				handleSelect();
				break;
			default:
				break;
			}	
		}
		this.pressedLastTime = pressedKeys;
	}
	
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder("MenuReferee with actions: ");
		for (MenuAction a : this.menuActions) {
			toReturn.append(a.toString());
		}
		
		return toReturn.toString();
	}
	

}
