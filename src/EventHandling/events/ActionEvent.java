package EventHandling.events;

import java.util.List;

import gameObjects.Sprite;

public class ActionEvent extends Event {
	public Sprite currentSprite;
	public List<Character> pressedKeys;
	public ActionEvent(Sprite s, List<Character> pressedKeys) {
		this.currentSprite = s;
		this.pressedKeys = pressedKeys;
	}

}
