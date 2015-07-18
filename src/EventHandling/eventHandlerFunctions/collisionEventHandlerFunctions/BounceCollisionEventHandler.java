package EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions;

import EventHandling.events.CollisionEvent;
import shapes.Impulse;
import shapes.Shape;
import geometryHelp.Vector;

public class BounceCollisionEventHandler extends CollisionEventHandlerFunction {

	@Override
	public void handleCollisionEvent(CollisionEvent cEvent) {
		Impulse impulse = new Impulse(cEvent.pointOfCollision, cEvent.MTV, cEvent.s1,
				cEvent.s2);
		Impulse oImpulse = new Impulse(cEvent.pointOfCollision, cEvent.MTV, cEvent.s2,
				cEvent.s1);

		cEvent.s1.applyImpulse(impulse);
		cEvent.s2.applyImpulse(oImpulse);
	}

}