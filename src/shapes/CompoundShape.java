package shapes;

import java.util.ArrayList;
import java.util.List;

import topLevel.Renderer;
import gameActions.ControlScheme;
import gameReferee.GameApplet;
import gameReferee.PhysicsReferee;
import geometryHelp.GeometryHelpers;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

public class CompoundShape extends Shape {
	private List<Shape> constituentShapes;

	public CompoundShape(PhysicsReferee ref, GameApplet gApp, double x,
			double y, ControlScheme cScheme, List<Shape> constituentShapes) {
		super(ref, gApp, x, y, cScheme);
		this.position = this.getCentroid();
	}

	@Override
	protected Line projectOntoVector(Vector toProjectOnto) {
		//TODO
		for (Shape currShape : constituentShapes) {
			Line currProj = currShape.projectOntoVector(toProjectOnto);
			currProj.getFurtherLeftPoint(toProjectOnto);
		}
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
	public void rotateAroundPoint(Point point, double theta) {
		for (Shape currShape : this.constituentShapes) {
			currShape.rotateAroundPoint(point, theta);
		}
	}

	@Override
	public void render(Renderer rend) {
		for (Shape currShape : this.constituentShapes) {
			currShape.render(rend);
		}
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
}
