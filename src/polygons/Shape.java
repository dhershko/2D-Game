package polygons;

import gameActions.ControlScheme;
import gameObjects.DynamicObject;
import gameReferee.GameApplet;
import gameReferee.Referee;

import java.util.ArrayList;
import java.util.List;

public abstract class Shape extends DynamicObject {

	public Shape(Referee ref, GameApplet gApp, float x, float y,
			ControlScheme cScheme) {
		super(ref, gApp, x, y, cScheme);
	}

	protected abstract Line projectOntoVector(Vector toProjectOnto);

	protected abstract List<Vector> getCollisionAxis(Shape other);


	public abstract void render(GameApplet gApp);


	public static List<Vector> getPerpVectors(List<Vector> vects){
		List<Vector> toReturn = new ArrayList<Vector>();
		for (Vector vec: vects) {
			toReturn.add(vec.getPerpVector());
		}

		return toReturn;
	}
	public Vector getMTV(Shape oShape) {

		List<Vector> allAxisVectors = this.getCollisionAxis(oShape);
		allAxisVectors.addAll(oShape.getCollisionAxis(this));

		double MTVMag = Double.MAX_VALUE;
		Vector MTV = null;

		for (Vector axis : allAxisVectors) {
			Line thisProjected = this.projectOntoVector(axis);
			Line otherProjected = oShape.projectOntoVector(axis);

			Double currentMag = thisProjected.MTVScalar(axis, otherProjected);

			if (currentMag == null) return null;
			
			
			//Render potential MTV vectors
//			if (this.cScheme != null)  {
//				gApp.stroke(255, 1, 1);
//				axis.getUnitVector().timesScalar(currentMag).render(gApp, x, y);
//				gApp.stroke(1, 255, 1);
//			}
			
			if (Math.abs(currentMag) < Math.abs(MTVMag)) {
				MTVMag = currentMag;
				MTV = axis.getUnitVector();
			}

			//			if (this == this.ref.players.get(0)) MTV.timesScalar(MTVMag).render(gApp, xPos, yPos);;
		}

//		if (this.cScheme != null) MTV.timesScalar(MTVMag).render(gApp, x, y);

		return MTV.timesScalar(MTVMag);
	}

	public boolean isCollidingWith(Shape other) {
		return (this.collisionMTV(other) == null);
	}

	public Vector collisionMTV(Shape other) {
		if (this == other) return null;

		return this.getMTV(other);
	}

	public void translate(double dX, double dY) {
		this.x += dX;
		this.y += dY;
	}

	@Override
	protected void fixCollision(DynamicObject dOb) {

		if (dOb instanceof Shape) {
			Shape oShape = (Shape) dOb;

			Vector MTV = this.collisionMTV(oShape);
			if (MTV != null) {
//				this.gApp.stroke(255, 1, 1);
				this.translate(MTV.x/2, MTV.y/2);
				oShape.translate(-MTV.x/2, -MTV.y/2);
//				this.xVel += MTV.getUnitVector().x*.1;
//				this.yVel += MTV.getUnitVector().y*.1;
//				oShape.xVel -= MTV.getUnitVector().x*.1;
//				oShape.yVel -= MTV.getUnitVector().y*.1;
			}
		}


	}
}
