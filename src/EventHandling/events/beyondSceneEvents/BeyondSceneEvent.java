package EventHandling.events.beyondSceneEvents;

import EventHandling.events.CollisionEvent;
import gameObjects.Sprite;
import geometryHelp.Vector;

public class BeyondSceneEvent extends CollisionEvent {
	public Sprite beyondScene;
	public BeyondSceneEvent(Sprite beyond) {
		super(beyond, beyond, new Vector(0 ,0));
	}
}
