package shapes;

import java.util.ArrayList;
import java.util.List;

import topLevel.Renderer;
import geometryHelp.GeometryHelpers;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

public class LineShape extends Shape {
	Point p1;
	Point p2;

	public LineShape(Point p1, Point p2) {
		super(p1.add(p2).timesScalar(1.0/2.0));
		this.p1 = p1;
		this.p2 = p2;
	}

	public Line toLine() {
		return new Line(p1, p2);
	}

	@Override
	protected Line projectOntoVector(Vector toProjectOnto) {
		return this.toLine().projectOntoVector(toProjectOnto);
	}

	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		List<Vector> toReturn = new ArrayList<Vector>();

		toReturn.add(new Vector(p1.x-p2.x, p1.y-p2.y).getPerpVector());
		return toReturn;
	}

	@Override
	protected double getInertia() {
		return .0001;
	}

	@Override
	public Point getCentroid() {
		return new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
	}

	@Override
	public void render(Renderer rend) {
		rend.rLine(this.toLine());
	}

	@Override
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
		List<Point> toReturn = new ArrayList<Point>();
		
		for (Point v : new Point[] {p1, p2}) {
			ShapePoint sP = v.toShapePoint();
			if (sP.isCollidingWith(other)) {
				toReturn.add(v);
			}
		}
		
		return toReturn;
	}
	
	@Override
	public String toString() {
		return "LineShape from " + p1 + " to " + p2;
	}

	@Override
	protected Point getPointOnShapeClosestToPoint(Point otherPoint) {
		List<Point> linePoints = new ArrayList<Point>();
		linePoints.add(this.p1);
		linePoints.add(this.p2);
		return GeometryHelpers.getPointClosestClosestFromListOfPoints(otherPoint, linePoints);
	}

	@Override
	public double getArea() {
		return 1;
	}

	@Override
	protected List<Point> getConstituentPoints() {
		// TODO Auto-generated method stub
		return null;
	}



}
