package shapes;

import gameReferee.GameApplet;
import gameReferee.Referee;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

import java.util.ArrayList;
import java.util.List;

import topLevel.Renderer;

public class Circle extends Shape {
	public Circle(Point position, double rad) {
		this.position = position;
		this.radius = rad;
	}

	public double radius;
	
/**
 * 
 * @param ref
 * @param gApp
 * @param x
 * @param y
 * @param radius
 * @param cScheme
 */


	@Override
	protected Line projectOntoVector(Vector toProjectOnto) {
		Point leftPoint = this.position.projectOnto(toProjectOnto);
		Point rightPoint = this.position.projectOnto(toProjectOnto);

		Vector moveCenterToLeft = new Vector(toProjectOnto.x, toProjectOnto.y);
		moveCenterToLeft.setLength(radius/2);
		Vector moveCenterToRight = new Vector(-toProjectOnto.x, -toProjectOnto.y);
		moveCenterToRight.setLength(radius/2);
		
		leftPoint.translate(moveCenterToLeft);
		rightPoint.translate(moveCenterToRight);
		Line toReturn = new Line(leftPoint, rightPoint);
		return toReturn;
	}

	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		Point pointOnOtherShapeClosest = other.getPointOnShapeClosestToPoint(this.position);
		Vector collisionAxis = new Vector(this.position.x-pointOnOtherShapeClosest.x, this.position.y-pointOnOtherShapeClosest.y);
		if (collisionAxis.isZero()) {
			collisionAxis = new Vector(1, 1);
		}
		List<Vector> toReturn = new ArrayList<Vector>();
		toReturn.add(collisionAxis);
		return toReturn;
	}

	@Override
	protected double getInertia() {
		return 1;
	}

	@Override
	public Point getCentroid() {
		return this.position;
	}

	@Override
	public void render(Renderer rend) {
		rend.rCircle(position, radius);
	}

	@Override
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
		List<Point> toReturn = new ArrayList<Point>();
		toReturn.add(other.getPointOnShapeClosestToPoint(this.position));
		return toReturn;
	}

	@Override
	protected Point getPointOnShapeClosestToPoint(Point otherPoint) {
		Vector translateCenter = new Vector(otherPoint.x - this.position.x, otherPoint.y - this.position.y);;
		translateCenter.setLength(radius);
		Point toReturn = new Point(this.position.x, this.position.y);
		toReturn.translate(translateCenter);
		return toReturn;
	}


	@Override
	public double getArea() {
		return Math.PI*Math.pow(this.radius, 2);
	}

	@Override
	protected List<Point> getConstituentPoints() {
		List<Point> toReturn = new ArrayList<Point>();
		toReturn.add(position);
		return toReturn;
	}
	
	@Override
	public String toString() {
		return "Circle at " + this.position;
	}

}
