package EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions;

import EventHandling.eventHandlerFunctions.EventHandlerFunction;
import EventHandling.events.CollisionEvent;


public abstract class CollisionEventHandlerFunction extends EventHandlerFunction {
	public abstract void handleCollisionEvent(CollisionEvent cEvent);
}
