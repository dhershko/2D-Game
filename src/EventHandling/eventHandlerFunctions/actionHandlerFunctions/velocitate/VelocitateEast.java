package EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate;

import gameObjects.Sprite;

public class VelocitateEast extends Velocitate{

	public VelocitateEast(double delta) {
		super(delta);
	}

	@Override
	public void handleActionEvent(Sprite currentSprite) {
		currentSprite.vel.x += delta;
	}
}
