package shapes;

import gameActions.ControlScheme;
import gameObjects.GameObject;
import gameReferee.GameApplet;
import gameReferee.Referee;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class Shape extends GameObject {


	float red = 0;
	float green = 255;
	float blue = 0;
	

	public Shape(Referee ref, GameApplet gApp, double x, double y,
			ControlScheme cScheme) {
		super(ref, gApp, x, y, cScheme);
	}

	protected abstract Line projectOntoVector(Vector toProjectOnto);

	protected abstract List<Vector> getCollisionAxis(Shape other);

	protected abstract double getInertia();

	protected abstract Point getCentroid();

	@Override
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
		pointsOfCollision.addAll(other.getPointsOfCollison(this, MTV));

		double pointX = 0.0;
		double pointY = 0.0;

		//TODO fix this bug so solution isn't hard coded
		if (pointsOfCollision.isEmpty()) {
			System.out.println("Shape: no points for collision, consider reducing terminal velocity");			
			pointsOfCollision.add(this.position);
			pointsOfCollision.add(other.position);
		}
		
		for (Point p : pointsOfCollision) {
			pointX += p.x;
			pointY += p.y;
		}
		
		pointX *= 1.0/pointsOfCollision.size();
		pointY *= 1.0/pointsOfCollision.size();

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
		if (MTV != null) MTV.setLength(MTVMag);
		return MTV;
	}

	public boolean isCollidingWith(Shape other) {
		return (this.collisionMTV(other) != null);
	}

	public Vector collisionMTV(Shape other) {
		if (this == other) return null;
		else {
			Vector MTV = this.getMTV(other);
			return MTV;
		}
	}

	protected void setPositionToCentroid() {
		Point centroid = this.getCentroid();
		this.position = centroid;
	}

	@Override
	public void fixCollision(GameObject dOb) {

		if (dOb instanceof Shape) {
			Shape oShape = (Shape) dOb;


			Vector MTV = this.collisionMTV(oShape);
			if (MTV != null) {

				
				
				Point pointOfCollision = getPointOfCollison(oShape, MTV);
				
				//RENDER POINT OF COLLISION
//				this.gApp.stroke(255, 1, 1);
//				pointOfCollision.render(gApp);
//				this.gApp.stroke(1, 255, 1);

				Impulse impulse = new Impulse(pointOfCollision, MTV, this, oShape);
				Impulse oImpulse = new Impulse(pointOfCollision, MTV, oShape, this);

				this.applyImpulse(impulse);
				oShape.applyImpulse(oImpulse);


				//Move objects of one another

				Vector thisProjVel = this.vel.getProjection(MTV);
				Vector otherProjVel = oShape.vel.getProjection(MTV);

				Double percentInMTVDir = .5;
				if (!thisProjVel.isZero() && !otherProjVel.isZero()) {
					percentInMTVDir = thisProjVel.getLength()/(thisProjVel.getLength()+otherProjVel.getLength());

				}

				Vector MTVInDir = MTV.timesScalar(percentInMTVDir);
				Vector MTVOtherDir = MTV.timesScalar(-1.0*(1.0-percentInMTVDir));
				this.translate(MTVInDir.timesScalar(1));
				oShape.translate(MTVOtherDir.timesScalar(1));
			}
		}


	}
}