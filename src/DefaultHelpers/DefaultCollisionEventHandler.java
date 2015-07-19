package DefaultHelpers;

import EventHandling.EventHandler;
import EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate.VelocitateEast;
import EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate.VelocitateNorth;
import EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate.VelocitateSouth;
import EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate.VelocitateWest;
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
		this.addWASDMove(PropertyKeyWords.CONTROLLED, .3);
	}
	
	
	public void addWASDMove(String controlProperty, double velMag) {
		this.addActionEventHandler(controlProperty, 'd', new VelocitateEast(velMag));
		this.addActionEventHandler(controlProperty, 's', new VelocitateSouth(velMag));
		this.addActionEventHandler(controlProperty, 'a', new VelocitateWest(velMag));
		this.addActionEventHandler(controlProperty, 'w', new VelocitateNorth(velMag));
	}
}
