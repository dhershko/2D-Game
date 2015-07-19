package shapes;

import java.util.ArrayList;
import java.util.List;

import topLevel.Renderer;
import gameReferee.GameApplet;
import geometryHelp.GeometryHelpers;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

public class CompoundShape extends Shape {
	private List<Shape> constituentShapes;

	public CompoundShape(List<Shape> constituentShapes) {
		super(null);
		this.constituentShapes = constituentShapes;
		this.position = this.getCentroid();
	}

	@Override
	protected Line projectOntoVector(Vector toProjectOnto) {
		//TODO implement -- pretty sure it's not used since getMTV is overrriden
		return new Line(new Point(0,0), new Point(1, 1));
	}

	@Override
	public Vector getMTV(Shape otherShape) {
		Vector toReturn = null;
		for (Shape currShape: this.constituentShapes) {
			Vector currMTV = currShape.getMTV(otherShape);
			if (currMTV != null && (toReturn == null || currMTV.getLength() < toReturn.getLength())) {
				toReturn = currMTV;
			}
		}
		return toReturn;
	}

	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		List<Vector> toReturn = new ArrayList<Vector>();
		for (Shape currShape : this.constituentShapes) {
			toReturn.addAll(currShape.getCollisionAxis(other));
		}
		return toReturn;
	}

	@Override
	protected double getInertia() {
		double toReturn = 0.0;
		for (Shape currShape : this.constituentShapes) {
			toReturn += currShape.getInertia();
		}
		return toReturn;
	}

	@Override
	protected Point getPointOnShapeClosestToPoint(Point otherPoint) {
		List<Point> allClosestPoints = new ArrayList<Point>();
		for (Shape currShape : this.constituentShapes) {
			allClosestPoints.add(currShape.getPointOnShapeClosestToPoint(otherPoint));
		}
		return GeometryHelpers.getPointClosestClosestFromListOfPoints(otherPoint, allClosestPoints);
	}

	@Override
	public Point getCentroid() {
		Point toReturn = new Point(0.0, 0.0);
		for (Shape currShape : this.constituentShapes) {
			toReturn = toReturn.add(currShape.getCentroid().timesScalar(currShape.getMass()));
		}
		return toReturn.timesScalar(1.0/this.getMass());
	}



	@Override
	public void render(Renderer rend) {
		for (Shape currShape : this.constituentShapes) {
			currShape.render(rend);
		}
		this.position.toShapePoint().render(rend);
	}

	@Override
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
		List<Point> toReturn = new ArrayList<Point>();
		for (Shape currShape : this.constituentShapes) {
			toReturn.addAll(currShape.getPointsOfCollison(other, MTV));
		}
		return toReturn;
	}

	@Override
	public double getMass() {
		double toReturn = 0.0;
		for (Shape currShape : this.constituentShapes) {
			toReturn += currShape.getMass();
		}
		return toReturn;
	}

	@Override
	protected List<Point> getConstituentPoints() {
		List<Point> toReturn = new ArrayList<Point>();
		for (Shape currShape : this.constituentShapes) {
			toReturn.addAll(currShape.getConstituentPoints());
		}
		return toReturn;
	}

	@Override
	public double getArea() {
		double toReturn = 0;
		for (Shape currShape : this.constituentShapes) {
			toReturn += currShape.getArea();
		}
		return toReturn;
	}
}
