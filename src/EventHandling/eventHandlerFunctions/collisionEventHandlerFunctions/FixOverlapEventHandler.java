package EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions;

import EventHandling.events.CollisionEvent;
import shapes.Shape;
import geometryHelp.Vector;

public class FixOverlapEventHandler extends CollisionEventHandlerFunction {

	@Override
	public void handleCollisionEvent(CollisionEvent cEvent) {
		// RENDER POINT OF COLLISION
		// this.gApp.stroke(255, 1, 1);
		// pointOfCollision.render(gApp);
		// this.gApp.stroke(1, 255, 1);
		Shape shape = cEvent.s1.hitBox;
		Shape oShape = cEvent.s2.hitBox;
		// Move objects off one another
		Vector thisProjVel = cEvent.s1.vel.getProjection(cEvent.MTV);
		Vector otherProjVel = cEvent.s2.vel.getProjection(cEvent.MTV);

		Double percentInMTVDir = .5;
		if (!thisProjVel.isZero() && !otherProjVel.isZero()) {
			percentInMTVDir = thisProjVel.getLength()
					/ (thisProjVel.getLength() + otherProjVel
							.getLength());

		}

		Vector MTVInDir = cEvent.MTV.timesScalar(percentInMTVDir);
		Vector MTVOtherDir = cEvent.MTV.timesScalar(-1.0
				* (1.0 - percentInMTVDir));
		shape.translate(MTVInDir.timesScalar(1));
		oShape.translate(MTVOtherDir.timesScalar(1));	
	}

}
