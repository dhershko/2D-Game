package geometryHelp;

import java.util.ArrayList;
import java.util.List;

import gameReferee.GameApplet;
import gameReferee.Referee;
import processing.core.PApplet;
import shapes.ShapePoint;

public class Point {
	public  Point(double x, double y) {

		if (Double.isNaN(x) || Double.isNaN(y)) {
			try {
				throw new NanPointException();
			} catch (NanPointException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		this.x = x;
		this.y = y;
	}

	public double x;
	public double y;



	public Vector toVector() {
		return new Vector(this.x, this.y);
	}

	public Point add(Vector vec) {
		return new Point(x + vec.x, y + vec.y);
	}
	public Point add(Point p) {
		return new Point(x + p.x, y + p.y);
	}
	public Point timesScalar(double times) {
		return new Point(x*times, y*times);
	}
	
	public Point getThisPointRelativeTo(Point other) {
		return new Point(this.x - other.x, this.y - other.y);
	}

	public ShapePoint toShapePoint() {
		return new ShapePoint(this);
	}

	public double distanceTo(Point otherPoint) {
		return Math.sqrt(Math.pow(this.x-otherPoint.x,2) + Math.pow(this.y-otherPoint.y,2));
	}

	public void translate(Vector vec) {
		this.x = this.x + vec.x;
		this.y = this.y + vec.y;
	}

	public double toIntervalPoint(Vector vec) {
		Line l = new Line(this, this);
		Interval i = new Interval(l, vec);
		return i.min;
	}

	public double distanceAlongVector(Vector vec) {
		//		double toReturn = this.toVector().getProjectionScalar(vec)*this.toVector().getLength();
		//		return toReturn;
		Vector projection = this.toVector().getProjection(vec);
		//Projection in opposite direction
		if (projection.inOppositeDirection(vec)) {
			return -projection.getLength();
		}

		//Projection in opposite direction
		return projection.getLength();

	}
	public void rotateAround(Point toRotateAround, double theta) {
			Point relativeToCentroid = this.getThisPointRelativeTo(toRotateAround);
			this.x = toRotateAround.x + relativeToCentroid.x*Math.cos(theta) - relativeToCentroid.y*Math.sin(theta);
			this.y = toRotateAround.y + relativeToCentroid.x*Math.sin(theta) + relativeToCentroid.y*Math.cos(theta);
	}
	
	public Point projectOnto(Vector axis){
		return this.toVector().getProjection(axis).toPoint();	
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";

	}

	public void render(GameApplet gApp) {
		gApp.pushMatrix();



		gApp.translate(this.x, this.y);
		gApp.beginShape();

		gApp.ellipse(0, 0, 5, 5);

		gApp.popMatrix();

	}

}
