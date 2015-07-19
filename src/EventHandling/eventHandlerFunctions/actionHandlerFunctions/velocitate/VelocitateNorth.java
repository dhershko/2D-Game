package EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate;

import gameObjects.Sprite;

public class VelocitateNorth extends Velocitate {

	public VelocitateNorth(double delta) {
		super(delta);
	}

	@Override
	public void handleActionEvent(Sprite currentSprite) {
		currentSprite.vel.y -= delta;
	}
}
