package EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate;

import EventHandling.eventHandlerFunctions.actionHandlerFunctions.ActionEventHandlerFunction;

public abstract class Velocitate extends ActionEventHandlerFunction{
	protected double delta;
	
	public Velocitate(double delta) {
		this.delta = delta;
	}
}
