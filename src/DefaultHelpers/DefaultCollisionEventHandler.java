package DefaultHelpers;

import EventHandling.EventHandler;
import EventHandling.PropertyKeyWords;
import EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions.BounceSceneReturn;
import EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions.ReturnScene;
import EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions.RoundSceneReturn;
import EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions.BounceCollisionEventHandler;
import EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions.FixOverlapEventHandler;


public class DefaultCollisionEventHandler extends EventHandler {
	public DefaultCollisionEventHandler() {
		super();
		this.addCollisionFunctionToCall(PropertyKeyWords.COLLIDABLE, PropertyKeyWords.COLLIDABLE, new FixOverlapEventHandler());
		this.addCollisionFunctionToCall(PropertyKeyWords.COLLIDABLE, PropertyKeyWords.COLLIDABLE, new BounceCollisionEventHandler());
		this.addOffSceneFunctionToCall(PropertyKeyWords.ROUNDSCENE, new BounceSceneReturn());
	}
}
