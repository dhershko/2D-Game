package EventHandling.eventHandlerFunctions.actionHandlerFunctions;

import gameObjects.Sprite;
import EventHandling.eventHandlerFunctions.EventHandlerFunction;

public abstract class ActionEventHandlerFunction extends EventHandlerFunction {
	public abstract void handleActionEvent(Sprite currentSprite);
}