package EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions;

import gameObjects.Sprite;
import geometryHelp.Vector;
import EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions.CollisionEventHandlerFunction;
import EventHandling.events.CollisionEvent;

public class BounceSceneReturn extends BeyondSceneEventHandlerFunction{

	@Override
	public void handleCollisionEvent(CollisionEvent cEvent) {
		Sprite s = cEvent.s1;
		if (s.position.x >= s.ref.sceneWidth)  {
			s.translate(new Vector(-(s.position.x - s.ref.sceneWidth), 0));
			s.vel.x = -s.vel.x;
		}
		else if (s.position.x < 0) {
			s.translate(new Vector(-(s.position.x), 0));
			s.vel.x = -s.vel.x;
		}
		if (s.position.y >= s.ref.sceneHeight)  {
			s.translate(new Vector(0, -(s.position.y - s.ref.sceneHeight)));
			s.vel.y = -s.vel.y;
		}
		else if (s.position.y < 0) {
			s.translate(new Vector(0, -s.position.y));
			s.vel.y = -s.vel.y;
		}
	}

}
