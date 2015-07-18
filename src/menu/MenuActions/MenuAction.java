package menu.MenuActions;

import topLevel.Renderer;
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

	public void render(Renderer rend, double vertOffset, double textHeight) {
		String toDisplay = this.displayName;
		if (isSelected) toDisplay = "\u21FE" + toDisplay;
		rend.rText(toDisplay, vertOffset, textHeight);
	}
	
	
	@Override
	public String toString() {
		return "MenuAction";
	}
	
	
}
