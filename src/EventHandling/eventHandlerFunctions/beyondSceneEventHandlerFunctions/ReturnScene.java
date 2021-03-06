package EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions;

import gameObjects.Sprite;
import geometryHelp.Vector;
import EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions.CollisionEventHandlerFunction;
import EventHandling.events.CollisionEvent;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public class ReturnScene extends BeyondSceneEventHandlerFunction{

	@Override
	public void handleBeyondSceneEvent(BeyondSceneEvent e) {
		Sprite s = e.beyondScene;
		if (s.hitBox.position.x >= s.ref.sceneWidth)  {
			s.translate(new Vector(-s.hitBox.position.x + s.ref.sceneWidth-1, 0));
		}
		else if (s.hitBox.position.x < 0) {
			s.translate(new Vector(-(s.hitBox.position.x), 0));
		}
		if (s.hitBox.position.y >= s.ref.sceneHeight)  {
			s.translate(new Vector(0, -s.hitBox.position.y+ s.ref.sceneHeight-1));
		}
		else if (s.hitBox.position.y < 0) {
			s.translate(new Vector(0, -(s.hitBox.position.y)));
		}
	}

}