package EventHandling.events.beyondSceneEvents;

import EventHandling.events.CollisionEvent;
import EventHandling.events.Event;
import gameObjects.Sprite;
import geometryHelp.Vector;

public class BeyondSceneEvent extends Event {
	public Sprite beyondScene;
	public BeyondSceneEvent(Sprite beyondScene) {
		this.beyondScene = beyondScene;
	}
}
