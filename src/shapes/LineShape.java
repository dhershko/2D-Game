package shapes;

import java.util.ArrayList;
import java.util.List;

import gameActions.ControlScheme;
import gameReferee.GameApplet;
import gameReferee.Referee;
import geometryHelp.GeometryHelpers;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

public class LineShape extends Shape {
	Point p1;
	Point p2;

	public LineShape(Referee ref, GameApplet gApp,
			ControlScheme cScheme, Point p1, Point p2) {
		super(ref, gApp, 0, 0, cScheme);
		this.p1 = p1;
		this.p2 = p2;
		this.setPositionToCentroid();
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
	protected Point getCentroid() {
		return new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
	}

	@Override
	public void rotate(double theta) {
		for (Point p : new Point[] {p1, p2}) {
			Point relativeToCentroid = p.getThisPointRelativeTo(this.position);
			p.x = this.position.x + relativeToCentroid.x*Math.cos(theta) - relativeToCentroid.y*Math.sin(theta);
			p.y = this.position.y + relativeToCentroid.x*Math.sin(theta) + relativeToCentroid.y*Math.cos(theta);
		}
	}

	@Override
	public void render(GameApplet gApp) {
		this.gApp.line(p1.x, p1.y, p2.x, p2.y);
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
	public void translate(Vector vec) {	
		this.position.translate(vec);
		p1.translate(vec);
		p2.translate(vec);
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

}
