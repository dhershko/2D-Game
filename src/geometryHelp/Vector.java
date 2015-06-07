package geometryHelp;

import gameReferee.GameApplet;
import processing.core.PApplet;

public class Vector {
	public double x;
	public double y;

	public  Vector(double x, double y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			try {
				throw new NanVectorException();
			} catch (NanVectorException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		this.x = x;
		this.y = y;
		
		this.x = x;
		this.y = y;
	}

	public Vector getPerpVector() {
		return new Vector(this.y, -this.x);
	}
	
	public Vector add(Vector vect) {
		return new Vector(this.x + vect.x, this.y + vect.y);
	}
	
	public void setLength(double length) {
		Vector newS = this.getUnitVector().timesScalar(length);
		this.x = newS.x;
		this.y = newS.y;
	}
	
	public Vector minus(Vector vect) {
		return new Vector(this.x- vect.x, this.y - vect.y);
	}

	public double getLength() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public double crossProduct(Vector o) {
		return this.x*o.y-o.x*this.y;
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
		double theta = Math.atan2(y, x);
		if (theta < 0) theta += 2*Math.PI;
		return theta;
	}

	public Point toPoint() {
		return new Point(this.x, this.y);
	}
	
	public boolean isZero() {
		return this.x == 0 && this.y == 0;
	}
	
	public double angleBetween(Vector vec) {
		double toReturn = this.getTheta()-vec.getTheta();
		return Math.abs(toReturn);
	}
	
	public boolean inOppositeDirection(Vector vec) {
		double angleBetween = this.angleBetween(vec);
		return angleBetween > Math.PI/2;
	}


	public void render(GameApplet app) {
		app.stroke(255,1,1);

		app.pushMatrix();
		app.line(0, 0, x,y);
		app.point(x, y);
		app.popMatrix();
	}

	public void render(GameApplet app, double xOffset, double yOffset) {
		
		this.render(app, (float) xOffset, (float) yOffset);
	}
	public void render(GameApplet app, float xOffset, float yOffset) {
		
		float arrowSize = 5;
		
		app.pushMatrix();
		app.translate(xOffset, yOffset);
		app.line(0, 0, x,y);

		double theta = this.getTheta();
		app.translate(x,y);

		app.rotate((float)theta);
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
	
	public static void main(String [] args) {
		Vector v1 = new Vector (5,0);
		Vector v2 = new Vector (0,5);
		Vector v3 = new Vector (-5,0);
		Vector v4 = new Vector (0,-5);
		Vector v5 = new Vector (1,1);
		
		System.out.println(!v1.inOppositeDirection(v1));
		System.out.println(!v1.inOppositeDirection(v2));
		System.out.println(!v1.inOppositeDirection(v5));
		System.out.println(!v2.inOppositeDirection(v5));
		System.out.println(v3.inOppositeDirection(v5));
		
		System.out.println(v1.getTheta() == 0);
		System.out.println(v2.getTheta() == Math.PI/2);

		System.out.println(v3.getTheta());
		System.out.println(v4.getTheta());



	}

}
