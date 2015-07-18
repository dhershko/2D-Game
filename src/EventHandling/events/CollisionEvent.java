package EventHandling.events;

import shapes.Shape;
import gameObjects.Sprite;
import geometryHelp.Point;
import geometryHelp.Vector;

public class CollisionEvent extends Event {
	public Sprite s1;
	public Sprite s2;
	
	public Point pointOfCollision;
	public Vector MTV;
	
	public CollisionEvent(Sprite sprite1, Sprite sprite2, Vector MTV) {
		this.s1 = sprite1;
		this.s2 = sprite2;
		this.MTV = MTV;
		if (MTV != null) pointOfCollision = sprite1.hitBox.getPointOfCollison(sprite2.hitBox, MTV);
	}
	
	
	@Override
	public String toString() {
		return "CollisionEvent between " + s1 + " and " + s2;
	}
}