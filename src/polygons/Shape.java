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

	protected abstract Point getCentroid();
	
	public abstract void rotate(double theta);

	public abstract void render(GameApplet gApp);

	public abstract List<Point> getPointsOfCollison(Shape other, Vector MTV);

	public static List<Vector> getPerpVectors(List<Vector> vects){
		List<Vector> toReturn = new ArrayList<Vector>();
		for (Vector vec: vects) {
			toReturn.add(vec.getPerpVector());
		}

		return toReturn;
	}
	
	public Point getPointOfCollison(Shape other, Vector MTV) {
		List<Point> pointsOfCollision = this.getPointsOfCollison(other, MTV);
		pointsOfCollision.addAll(other.getPointsOfCollison(other, MTV));
		
		double pointX = 0;
		double pointY = 0;
		
		for (Point p : pointsOfCollision) {
			pointX += p.x;
			pointY += p.y;
		}
		
		pointX *= 1/pointsOfCollision.size();
		pointY *= 1/pointsOfCollision.size();
		
		return new Point(pointX, pointY);
		
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
		else {
			Vector MTV = this.getMTV(other);

			//MTV from barely touching
			if (MTV == null || MTV.getLength() < Double.MIN_VALUE) {
				return null;
			}

			return MTV;
		}
	}



	@Override
	protected void fixCollision(DynamicObject dOb) {

		if (dOb instanceof Shape) {
			Shape oShape = (Shape) dOb;

			Vector MTV = this.collisionMTV(oShape);
			if (MTV != null) {
				
				
				//RENDER POINT OF COLLISION
				if (this.cScheme != null) {
					Point pointOfCollision = getPointOfCollison(oShape, MTV);
					this.gApp.stroke(255, 1, 1);
					pointOfCollision.render(gApp);
					this.gApp.stroke(1, 255, 1);
					System.out.println(pointOfCollision);


				}
				
//				Vector thisProjVel = this.vel.getProjection(MTV);
//				Vector otherProjVel = oShape.vel.getProjection(MTV);
//				
//				Double percentInMTVDir = thisProjVel.getLength()/(thisProjVel.getLength()+otherProjVel.getLength());
//				
//				Vector MTVInDir = MTV.timesScalar(percentInMTVDir);
//				Vector MTVOtherDir = MTV.timesScalar(-1.0*(1.0-percentInMTVDir));
//				this.translate(MTVInDir);
//				oShape.translate(MTVOtherDir);
//
//				Double COR = this.coefficentOfRestitution(oShape);
//
//				Double velChangeScalar = this.mass*oShape.mass*(1+COR)/(this.mass+oShape.mass);
//	
//				Vector thisImpulse = otherProjVel.minus(thisProjVel).timesScalar(velChangeScalar);
//
//				Vector oImpulse = thisProjVel.minus(otherProjVel).timesScalar(velChangeScalar);
//				
//				this.applyImpulse(thisImpulse);
//				oShape.applyImpulse(oImpulse);

			
			}
		}


	}
}
