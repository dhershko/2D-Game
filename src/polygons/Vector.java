package polygons;

import gameReferee.GameApplet;
import processing.core.PApplet;

public class Vector {
	public double x;
	public double y;

	public  Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector getPerpVector() {
		return new Vector(this.y, -this.x);
	}
	
	public Vector add(Vector vect) {
		return new Vector(this.x + vect.x, this.y + vect.y);
	}
	
	public Vector minus(Vector vect) {
		return new Vector(this.x- vect.x, this.y - vect.y);
	}

	public double getLength() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}

	public double dotProduct(Vector other) {
		return (x*other.x + y*other.y);
	}


	public Vector getUnitVector() {
		double length = this.getLength();
		return new Vector(this.x/length, this.y/length);
	}

	public Vector timesScalar(double scalar) {
		return new Vector(this.x*scalar, this.y*scalar);
	}

	public Vector getProjection(Vector toProjectOnto) {
		double scalarToMultBy = getProjectionScalar(toProjectOnto);
		return toProjectOnto.timesScalar(scalarToMultBy);
	}
	
	public Double getProjectionScalar(Vector toProjectOnto) {
		return this.dotProduct(toProjectOnto)/toProjectOnto.dotProduct(toProjectOnto);
	}

	public double getTheta() {
		double theta = Math.atan(y/(x+Double.MIN_VALUE));
		if (x < 0) theta += Math.PI;
		return theta;
	}

	public Point toPoint() {
		return new Point(this.x, this.y);
	}


	public void render(GameApplet app) {
		app.stroke(255,1,1);

		app.pushMatrix();
		app.line(0, 0, x,y);
		app.point(x, y);
		app.popMatrix();
	}


	public void render(GameApplet app, float xOffset, float yOffset) {
		
		float arrowSize = 5;
		
		app.pushMatrix();
		app.translate(xOffset, yOffset);
		app.line(0, 0, x,y);

		double theta = this.getTheta();
		app.translate(x,y);

		app.rotate((float)theta);
		//		app.rect(0,0, 20, 20);
		app.beginShape();
		app.vertex(0, 0);
		app.vertex(-arrowSize, -arrowSize/2);
		app.vertex(-arrowSize, arrowSize/2);
		app.endShape(app.CLOSE);

		app.popMatrix();
	}
	@Override
	public String toString() {
		return "Vector: (" + this.x + ", " + this.y + ")";

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			Vector oCast = (Vector) o;
			return oCast.x == this.x && oCast.y == this.y;
		}
		return false;
		
	}

}
