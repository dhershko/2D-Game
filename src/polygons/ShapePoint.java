package polygons;

import java.util.ArrayList;
import java.util.List;

import gameActions.ControlScheme;
import gameReferee.GameApplet;
import gameReferee.Referee;
import processing.core.PApplet;

public class ShapePoint extends Shape {
	public ShapePoint(Referee ref, GameApplet gApp, float x, float y,
			ControlScheme cScheme) {
		super(ref, gApp, x, y, cScheme);
	}
	
	public  ShapePoint(double x, double y) {
		super(null, null, x, y, null);
		this.x = x;
		this.y = y;
	}

	public double x;
	public double y;

	public Point toPoint() {
		return new Point(this.x, this.y);
	}


	public Vector toVector() {
		return new Vector(this.x, this.y);
	}
	
	public Point add(Vector vec) {
		return new Point(x + vec.x, y + vec.y);
	}
	
	public Point getThisPointRelativeTo(Point other) {
		return new Point(this.x - other.x, this.y - other.y);
	}

	public double distanceTo(Point otherPoint) {
		return Math.sqrt(Math.pow(this.x-otherPoint.x,2) + Math.pow(this.y-otherPoint.y,2));
	}
	
	public void translate(Vector vec) {
		this.x = this.x + vec.x;
		this.y = this.y + vec.y;
	}

	public double toIntervalPoint(Vector vec) {
		Line l = new Line(this.toPoint(), this.toPoint());
		Interval i = new Interval(l, vec);
		return i.min;
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

	@Override
	protected Line projectOntoVector(Vector toProjectOnto) {
		Point projection =  this.toVector().getProjection(toProjectOnto).toPoint();
		return new Line(projection, projection);
	}

	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		List<Vector> toReturn = new ArrayList<Vector>();
		return toReturn;
	}

	@Override
	protected Point getCentroid() {
		return this.toPoint();
	}

	@Override
	public void rotate(double theta) {
	}

	@Override
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
		// TODO Auto-generated method stub
		List<Point> toReturn = new ArrayList<Point>();
		toReturn.add(this.toPoint());
		return toReturn;
	}

	@Override
	protected double getInertia() {
		return 0;
	}
}
