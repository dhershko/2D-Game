package shapes;

import java.util.ArrayList;
import java.util.List;

import gameActions.ControlScheme;
import gameReferee.GameApplet;
import gameReferee.Referee;
import geometryHelp.Interval;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;
import processing.core.PApplet;
import topLevel.Renderer;

public class ShapePoint extends Shape {
//	public ShapePoint(PhysicsReferee ref, GameApplet gApp, float x, float y,
//			ControlScheme cScheme) {
//		super(ref, gApp, x, y, cScheme);
//	}
//	
//	public  ShapePoint(double x, double y) {
//		super(null, null, x, y, null);
//		this.position.x = x;
//		this.position.y = y;
//	}



	public ShapePoint(Point position) {
		super(position);
	}


	public Point toPoint() {
		return new Point(this.position.x, this.position.y);
	}


	public Vector toVector() {
		return new Vector(this.position.x, this.position.y);
	}

	public double toIntervalPoint(Vector vec) {
		Line l = new Line(this.toPoint(), this.toPoint());
		Interval i = new Interval(l, vec);
		return i.min;
	}

	@Override
	public String toString() {
		return "(" + position.x + ", " + position.y + ")";

	}
	
	public void render(Renderer rend) {
		rend.rPoint(this.toPoint());
//		double renderRadius = .1;
//		gApp.stroke(1, 255, 1);
//		gApp.pushMatrix();
//
//		
//
//		gApp.translate(this.position.x, this.position.y);
//		gApp.beginShape();
//		
//		gApp.ellipse(0, 0, renderRadius, renderRadius);
//		
//		gApp.popMatrix();

	}

	@Override
	protected Line projectOntoVector(Vector toProjectOnto) {
		Vector proj = this.toVector().getProjection(toProjectOnto);
		Point projection =  proj.toPoint();
		return new Line(projection, projection);
	}

	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		List<Vector> toReturn = new ArrayList<Vector>();
		return toReturn;
	}

	@Override
	public Point getCentroid() {
		return this.toPoint();
	}

	@Override
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
		List<Point> toReturn = new ArrayList<Point>();
		toReturn.add(this.toPoint());
		return toReturn;
	}

	@Override
	protected double getInertia() {
		return 1;
	}

	@Override
	protected Point getPointOnShapeClosestToPoint(Point otherPoint) {
		return this.toPoint();
	}

	@Override
	public double getMass() {
		return 1.0;
	}


	@Override
	protected List<Point> getConstituentPoints() {
		List<Point> toReturn = new ArrayList<Point>();
		toReturn.add(position);
		return toReturn;
	}

	@Override
	public double getArea() {
		return 1;
	}
}
