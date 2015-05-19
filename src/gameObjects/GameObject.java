package gameObjects;

import java.util.List;

import gameReferee.GameApplet;
import gameReferee.Referee;

public abstract class GameObject {
	protected Referee ref;
	public GameApplet gApp;
	
	public GameObject(Referee ref, GameApplet gApp) {
		this.ref = ref;
		this.gApp = gApp;
	}
	
	public abstract void render(GameApplet gapp);
	
	public abstract void update(List<Character> pressedKeys);
}
