package polygons;

import gameReferee.GameApplet;
import processing.core.PApplet;

public class Point {
	public double x;
	public double y;

	public  Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector toVector() {
		return new Vector(this.x, this.y);
	}


	public double distanceTo(Point otherPoint) {
		return Math.sqrt(Math.pow(this.x-otherPoint.x,2) + Math.pow(this.y-otherPoint.y,2));
	}



	public double distanceAlongVector(Vector vec) {
//		double toReturn = this.toVector().getProjectionScalar(vec)*this.toVector().getLength();
//		return toReturn;
		Vector projection = this.toVector().getProjection(vec);
		//Projection in opposite direction
		if (Math.abs(projection.getTheta() - vec.getTheta()) > .001) {
			return -projection.getLength();
		}

		//Projection in opposite direction
		return projection.getLength();

	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";

	}
	
	public void render(GameApplet gApp) {
		gApp.stroke(1, 255, 1);
		gApp.pushMatrix();

		

		gApp.translate(this.x, this.y);
		gApp.beginShape();
		
		gApp.ellipse(0, 0, 5, 5);
		
		gApp.popMatrix();

	}
}
