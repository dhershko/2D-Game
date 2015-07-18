package shapes;

import gameActions.ControlScheme;
import gameObjects.Sprite;
import gameReferee.GameApplet;
import gameReferee.Referee;
import geometryHelp.GeometryHelpers;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import topLevel.Renderer;

public class Polygon extends Shape {
	public List<Point> points;

	public Polygon(Point position, List<Point> points) {
		super(position);
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
	
	public Polygon(Point position, int numPoints, double radius) {
		super(position);
		this.points = new ArrayList<Point>();
		Random rand = new Random();

		//Get vertices
		float angle = (float) (Math.PI*2 / numPoints);
		for (float a = 0; a < Math.PI*2; a += angle) {

			float random = (float) (rand.nextFloat()*.5 + .5);


			float vertX = (float) (Math.cos(a) * radius * random);
			float vertY = (float) (Math.sin(a) * radius * random);
			points.add(new Point(position.x+vertX, position.y+vertY));
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

	public List<Vector> getVerticesVectorsRelativeToCent() {
		List<Vector> toReturn = new ArrayList<Vector>();
		for (Point p : this.points) {
			toReturn.add(p.getThisPointRelativeTo(this.position).toVector());
		}
		
		
		return toReturn;
	}

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
		Line toReturn = projectLinesOntoVector(lines, vec);
		return toReturn;
	}



	public Line projectLinesOntoVector(List<Line> lines, Vector vec) {
		List<Line> projectedLines = new ArrayList<Line>();

		//Project all lines
		for (Line line : lines) {
			projectedLines.add(line.projectOntoVector(vec));
		}

		//Find min and max points in lines along vectors
		double smallerScalarMult = Double.MAX_VALUE;
		double largerScalarMult = -Double.MAX_VALUE;
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
	
	public double getArea() {
		double toReturn = 0;
		
		for (int i = 0; i < this.points.size(); i++) {
			Point p1 = this.points.get(i);
			Point p2 = this.points.get((i+1)%this.points.size());
			toReturn += p1.x*p2.y-p1.y*p2.x;
		}
		
		
		toReturn = Math.abs(toReturn/2.0);
		return toReturn;
	}


	@Override
	public void render(Renderer rend) {
		rend.rPolygon(this.points);
		//		this.gApp.stroke(0);
		//		this.gApp.fill(red, green, blue);
		//		if (this.cScheme != null) {this.gApp.stroke(255, 0, 0);}
		//		gApp.pushMatrix();
//		gApp.beginShape();
//		for (Point point : this.points) {
//			gApp.vertex(point.x, point.y);
//		}
//		gApp.endShape(gApp.CLOSE);
//		gApp.popMatrix();
//		this.position.render(gApp);;
	}


	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		List<Vector> vectors = this.toVectors();
		return getPerpVectors(vectors);
	}

	@Override
	public Point getCentroid() {
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
	public List<Point> getPointsOfCollison(Shape other, Vector MTV) {
				
		List<Point> toReturn = new ArrayList<Point>();
				
		for (Point v : this.points) {
			ShapePoint sP = v.toShapePoint();
			if (sP.isCollidingWith(other)) {
				toReturn.add(v);
			}
		}
		
		return toReturn;
		
	}

	@Override
	protected double getInertia() {
		double toReturn = 0;
		double num = 0;
		double den = 0;
		List<Vector> verts =  this.getVerticesVectorsRelativeToCent();
		for (int i = 0; i < verts.size()-1; i++) {
			Vector v1 = verts.get(i);
			Vector v2 = verts.get((i+1)%verts.size());
			
			num += (v1.getLength() + v1.dotProduct(v2) + v2.getLength())*(v1.crossProduct(v2));
			den += v1.crossProduct(v2);
		}
		toReturn += num;
		toReturn *= 1.0/den;
		toReturn *= this.getMass()/6.0;
		return toReturn;
	}
	
	@Override
	public String toString() {
		return "Polygon at " + this.position;
	}

	@Override
	protected Point getPointOnShapeClosestToPoint(Point otherPoint) {
		return GeometryHelpers.getPointClosestClosestFromListOfPoints(otherPoint, this.points);
	}

	@Override
	protected List<Point> getConstituentPoints() {
		return this.points;
	}

}
