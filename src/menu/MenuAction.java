package menu;

import gameReferee.GameApplet;

public abstract class MenuAction {
	private String displayName;
	boolean isSelected;
	public abstract void doAction();

	public void render(GameApplet gApp, float vertOffset, float textHeight) {
			gApp.stroke(255, 0, 0);
		if (isSelected) {
			gApp.stroke(0, 255, 0);
		}
		gApp.textAlign(gApp.CENTER);
		gApp.textFont(gApp.createFont("Arial",textHeight,true));
		gApp.text(displayName, (float) (gApp.width/2.0), vertOffset);
	}
	
	
}
