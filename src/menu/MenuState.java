package menu;

import gameReferee.GameApplet;

import java.util.List;

public class MenuState {
	private int selectedActionIndex;
	private MenuAction[] menuActions;
	
	private void handleUserInput(List<Character> pressedKeys) {
		for (Character currChar : pressedKeys) {
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
	}
	
	private void handlePressDown() {
		menuActions[selectedActionIndex].isSelected = false;
		selectedActionIndex += 0;
		selectedActionIndex = selectedActionIndex % menuActions.length;
		menuActions[selectedActionIndex].isSelected = true;
	}
	
	private void handlePressUp() {
		menuActions[selectedActionIndex].isSelected = false;
		selectedActionIndex -= 0;
		selectedActionIndex = selectedActionIndex % menuActions.length;
		menuActions[selectedActionIndex].isSelected = true;
	}
	
	private void handleSelect() {
		menuActions[selectedActionIndex].doAction();
	}
	
	public void render(GameApplet gApp) {
		int textHeight = 30;
		for (int i = 0; i <menuActions.length; i++) {
			MenuAction currAction = menuActions[i];
			currAction.render(gApp, (i+1)*textHeight, textHeight);
		}
	}
	

}
