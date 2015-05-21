package polygons;

import gameActions.ControlScheme;
import gameObjects.DynamicObject;
import gameReferee.GameApplet;
import gameReferee.Referee;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Polygon extends Shape {

	private List<Point> points;

	/**
	 * 
	 * @param ref
	 * @param gApp
	 * @param x
	 * @param y
	 * @param cScheme
	 * @param points
	 */
	public Polygon(Referee ref, GameApplet gApp, ControlScheme cScheme, float x, float y, List<Point> points) {
		super(ref, gApp, x, y, cScheme);
		this.points = points;
		if (points.size() < 2) {
			try {
				throw new TooFewPointsForPolygonException();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		this.setPositionToCentroid();
	}

	public Polygon(Referee ref, GameApplet gApp, ControlScheme cScheme, float x, float y, int numPoints, double radius) {
		super(ref, gApp, x, y, cScheme);
		this.points = new ArrayList<Point>();

		//Get vertices
		float angle = (float) (Math.PI*2 / numPoints);
		for (float a = 0; a < Math.PI*2; a += angle) {

			float random = (float) (gApp.random((float) .5) + .5);

			float vertX = (float) (Math.cos(a) * radius * random);
			float vertY = (float) (Math.sin(a) * radius * random);
			points.add(new Point(x+vertX, y+vertY));
		}

		if (points.size() < 2) {
			try {
				throw new TooFewPointsForPolygonException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.setPositionToCentroid();


	}

	private void setPositionToCentroid() {
		Point centroid = this.getCentroid();
		this.position = centroid;
	}

//	public List<Point> getVerticesInAbsolute() {
//		List<Point> toReturn = new ArrayList<Point>();
//		for (Point p: points) {
//			toReturn.add(new Point(this.position.x + p.x, this.position.y + p.y));
//		}
//		return toReturn;
//	}
//
//	public static List<Point> getPointsInOffsetInSpace(List<Point> points, double xCenter, double yCenter) {
//		List<Point> toReturn = new ArrayList<Point>();
//		for (Point p: points) {
//			toReturn.add(new Point(xCenter + p.x, yCenter + p.y));
//		}
//		return toReturn;
//	}


	public List<Line> toLines() {
		List<Line> toReturn = new ArrayList<Line>();
		for (int i = 0; i < this.points.size(); i ++) {
			Point p1 = this.points.get(i);
			Point p2 = this.points.get((i+1)%this.points.size());

			Line toAdd = new Line(p1, p2);
			toReturn.add(toAdd);
		}

		return toReturn;
	}


	public List<Vector> toVectors() {
		List<Vector> toReturn = new ArrayList<Vector>();
		for (int i = 0; i < this.points.size(); i ++) {
			Point p1 = this.points.get(i);
			Point p2 = this.points.get((i+1)%this.points.size());

			Vector toAdd = new Vector(p1.x-p2.x, p1.y-p2.y);
			toReturn.add(toAdd);
		}

		return toReturn;
	}

	public static List<Vector> getPerpVectors(List<Vector> vects){
		List<Vector> toReturn = new ArrayList<Vector>();
		for (Vector vec: vects) {
			toReturn.add(vec.getPerpVector());
		}

		return toReturn;
	}

	@Override
	public Line projectOntoVector(Vector vec) {
		List<Line> lines = this.toLines();
		return projectLinesOntoVector(lines, vec);
	}



	public Line projectLinesOntoVector(List<Line> lines, Vector vec) {
		List<Line> projectedLines = new ArrayList<Line>();

		//Project all lines
		for (Line line : lines) {
			projectedLines.add(line.projectOntoVector(vec));
		}

		//Find min and max points in lines along vectors
		double smallerScalarMult = Float.MAX_VALUE;
		double largerScalarMult = -Float.MAX_VALUE;
		Point smallerPoint = null;
		Point largerPoint = null;
		for (Line projLine : projectedLines) {
			for (Point currPoint : new Point [] {projLine.p1, projLine.p2}) {
				double currScalarMult = currPoint.x + currPoint.y;

				if (currScalarMult < smallerScalarMult) {
					smallerScalarMult = currScalarMult;
					smallerPoint = currPoint;
				}

				if (currScalarMult > largerScalarMult) {
					largerScalarMult = currScalarMult;
					largerPoint = currPoint;
				} 
			}
		}

		if (smallerPoint == null) {
//			System.out.println(this.points);
//			System.out.println(vec);
		}

		return new Line(smallerPoint, largerPoint);
	} 


	@Override
	public void render(GameApplet gApp) {
		gApp.pushMatrix();
		gApp.beginShape();
		for (Point point : this.points) {
			gApp.vertex(point.x, point.y);
		}
		gApp.endShape(gApp.CLOSE);
		gApp.popMatrix();
		this.position.render(gApp);;
	}


	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		List<Vector> vectors = this.toVectors();
		return getPerpVectors(vectors);
	}

	@Override
	protected Point getCentroid() {
		double cX = 0;
		double cY = 0;
		double A = 0;

		for (int i = 0; i < this.points.size(); i ++) {
			Point p1 = this.points.get(i);
			Point p2 = this.points.get((i+1)%this.points.size());

			cX += (p1.x + p2.x) * (p1.x*p2.y-p2.x*p1.y);
			cY += (p1.y+p2.y)*(p1.x*p2.y-p2.x*p1.y);
			
			A += p1.x*p2.y - p2.x*p1.y;
		}
		A *= 1.0/2.0;
		
		cX *= 1/(6*A);
		cY *= 1/(6*A);
		Point toReturn = new Point(cX, cY);
		return toReturn;
	}
	
	@Override
	public void translate(Vector vec) {
		this.position.translate(vec);
		for(Point vert : this.points) {
			vert.translate(vec);
		}
		
	}

	@Override
	public void rotate(double theta) {
				
		for (Point vert : this.points) {
			Point relativeToCentroid = vert.getThisPointRelativeTo(this.position);
			vert.x = this.position.x + relativeToCentroid.x*Math.cos(theta) - relativeToCentroid.y*Math.sin(theta);
			vert.y = this.position.y + relativeToCentroid.x*Math.sin(theta) + relativeToCentroid.y*Math.cos(theta);
		}
	}

	@Override
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
				
		List<Point> toReturn = new ArrayList<Point>();
		
		Line thisProj = this.projectOntoVector(MTV);
		Interval thisInter = new Interval(thisProj, MTV);
		
		Line oProj = other.projectOntoVector(MTV);
		Interval oInter = new Interval(oProj, MTV);
		
		Interval overlapInterval = thisInter.getOverlap(oInter);
		
		for (Point v : this.points) {
			Double pointOnInterval = v.toIntervalPoint(MTV);
			if (overlapInterval.contains(pointOnInterval)) {
				toReturn.add(v);
			}
		}
		
		return toReturn;
		
	}


}
