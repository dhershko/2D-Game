package EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions;

import gameObjects.Sprite;
import EventHandling.eventHandlerFunctions.EventHandlerFunction;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public abstract class BeyondSceneEventHandlerFunction extends EventHandlerFunction {
	public abstract void handleBeyondSceneEvent(BeyondSceneEvent e);
}
