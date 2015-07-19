package EventHandling.eventHandlerFunctions.actionHandlerFunctions.velocitate;

import gameObjects.Sprite;

public class VelocitateSouth extends Velocitate {

	public VelocitateSouth(double delta) {
		super(delta);
	}

	@Override
	public void handleActionEvent(Sprite currentSprite) {
		currentSprite.vel.y += delta;
	}
}
