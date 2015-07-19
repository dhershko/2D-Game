package EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions;

import gameObjects.Sprite;
import geometryHelp.Vector;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public class BounceSceneReturn extends BeyondSceneEventHandlerFunction{

	@Override
	public void handleBeyondSceneEvent(BeyondSceneEvent e) {
		Sprite s = e.beyondScene;
		if (s.hitBox.position.x >= s.ref.sceneWidth)  {
			s.translate(new Vector(-(s.hitBox.position.x - s.ref.sceneWidth), 0));
			s.vel.x = -s.vel.x;
			s.vel.setLength(s.vel.getLength()*s.restitution);
		}
		else if (s.hitBox.position.x < 0) {
			s.translate(new Vector(-(s.hitBox.position.x), 0));
			s.vel.x = -s.vel.x;
			s.vel.setLength(s.vel.getLength()*s.restitution);
		}
		if (s.hitBox.position.y >= s.ref.sceneHeight)  {
			s.translate(new Vector(0, -s.hitBox.position.y + s.ref.sceneHeight));
			s.vel.y = -s.vel.y;
			s.vel.setLength(s.vel.getLength()*s.restitution);
		}
		else if (s.hitBox.position.y < 0) {
			s.translate(new Vector(0, -s.hitBox.position.y));
			s.vel.y = -s.vel.y;
			s.vel.setLength(s.vel.getLength()*s.restitution);
		}
	}
}
