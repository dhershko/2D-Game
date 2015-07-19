package EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate;

import gameObjects.Sprite;

public class VelocitateWest extends Velocitate {

	public VelocitateWest(double delta) {
		super(delta);
	}

	@Override
	public void handleActionEvent(Sprite currentSprite) {
		currentSprite.vel.x -= delta;
	}
}
