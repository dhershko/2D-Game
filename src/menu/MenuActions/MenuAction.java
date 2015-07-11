package menu.MenuActions;

import menu.MenuReferee;
import gameReferee.GameApplet;

public abstract class MenuAction {
	private String displayName;
	public boolean isSelected;
	public abstract void doAction();
	protected MenuReferee menRef;
	
	public void setMenRef(MenuReferee menRef) {
		this.menRef = menRef;
	}
	
	public MenuAction(String displayName, MenuReferee menRef) {
		this.menRef = menRef;
		this.menRef.AddAction(this);
		this.displayName = displayName;
	}

	public void render(GameApplet gApp, float vertOffset, float textHeight) {
		gApp.fill(255, 0, 0);
		if (isSelected) {
		gApp.fill(0, 255, 0);
		}
		gApp.textAlign(gApp.CENTER);
		gApp.textFont(gApp.createFont("Arial",textHeight,true));
		gApp.text(this.displayName, (float) (gApp.width/2.0), vertOffset);
	}
	
	
	@Override
	public String toString() {
		return "MenuAction";
	}
	
	
}
