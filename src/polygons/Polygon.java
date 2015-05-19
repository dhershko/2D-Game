package polygons;

import gameActions.ControlScheme;
import gameObjects.DynamicObject;
import gameReferee.GameApplet;
import gameReferee.Referee;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Polygon extends Shape {

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
	}


	private List<Point> points;




	public Polygon(Referee ref, GameApplet gApp, ControlScheme cScheme, float x, float y, int numPoints, double radius) {
		super(ref, gApp, x, y, cScheme);
		this.points = new ArrayList<Point>();

		//Get vertices
		float angle = (float) (Math.PI*2 / numPoints);
			for (float a = 0; a < Math.PI*2; a += angle) {
				
				float random = (float) (gApp.random((float) .5) + .5);
				
				float vertX = (float) (Math.cos(a) * radius * random);
				float vertY = (float) (Math.sin(a) * radius * random);
				points.add(new Point(vertX, vertY));
		}



		if (points.size() < 2)
			try {
				throw new TooFewPointsForPolygonException();
			} catch (Exception e) {
				e.printStackTrace();
			}


	}



	public List<Point> getPointsInAbsoluteSpace() {
		List<Point> toReturn = new ArrayList<Point>();
		for (Point p: this.points) {
			toReturn.add(new Point(p.x + x, p.y + y));
		}
		return toReturn;
	}


	public List<Line> toLines() {
		List<Point> pointsInAbsSpace = getPointsInAbsoluteSpace();
		List<Line> toReturn = new ArrayList<Line>();
		for (int i = 0; i < pointsInAbsSpace.size(); i ++) {
			Point p1 = pointsInAbsSpace.get(i);
			Point p2 = pointsInAbsSpace.get((i+1)%pointsInAbsSpace.size());

			Line toAdd = new Line(p1, p2);
			toReturn.add(toAdd);
		}

		return toReturn;
	}


	public List<Vector> toVectors() {
		List<Point> pointsInAbsSpace = getPointsInAbsoluteSpace();
		List<Vector> toReturn = new ArrayList<Vector>();
		for (int i = 0; i < pointsInAbsSpace.size(); i ++) {
			Point p1 = pointsInAbsSpace.get(i);
			Point p2 = pointsInAbsSpace.get((i+1)%pointsInAbsSpace.size());

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

	public Line projectOntoVector(Vector vec) {
		List<Line> lines = this.toLines();
		return projectLinesOntoVector(lines, vec);
	}



	public static Line projectLinesOntoVector(List<Line> lines, Vector vec) {
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


		return new Line(smallerPoint, largerPoint);
	} 

//	boolean collidingWith(Polygon other) {
//		if (this == other) return false;
//
//		List<Vector> axisVectors = getPerpVectors(this.toVectors());
//		axisVectors.addAll(getPerpVectors(other.toVectors()));
//		//						if (ref.players.get(0) == this) {
//		//							int i = 0;
//		//							for(Vector axis : axisVectors) {
//		//								Line thisProjected = this.projectOntoVector(axis);
//		//								Line otherProjected = other.projectOntoVector(axis);
//		//								thisProjected.render(gApp, i*10, i*10);
//		//								otherProjected.render(gApp, i*10, i*10);
//		//				//				System.out.println("thisProj: "+ thisProjected);
//		//				//				System.out.println("thatProj: "+ otherProjected);
//		//				
//		//								i++;
//		//							}
//		//						}
//		for (Vector axis : axisVectors) {
//
//			Line thisProjected = this.projectOntoVector(axis);
//			Line otherProjected = other.projectOntoVector(axis);
//
//			if (!thisProjected.overlapsLineAlongAxis(otherProjected, axis)){
//				return false;
//			}
//		}
//		return true;
//	}

//	@Override
//	public Vector getMTV(Shape oShape) {
//
//		Polygon other = (Polygon) oShape;
//		
//		List<Vector> allAxisVectors = getPerpVectors(this.toVectors());
//		allAxisVectors.addAll(getPerpVectors(other.toVectors()));
//
//		double MTVMag = Double.MAX_VALUE;
//		Vector MTV = null;
//		Vector usedAxis = null;
//
//		for (Vector axis : allAxisVectors) {
//			Line thisProjected = this.projectOntoVector(axis);
//			Line otherProjected = other.projectOntoVector(axis);
//
//			Double currentMag = thisProjected.MTVScalar(axis, otherProjected);
//
//			if (currentMag == null) return null;
//			
//			//RENDER VECTORS FOR PLAYER
//			
//			//Render potential MTV vectors
//			if (this.cScheme != null)  {
//				gApp.stroke(255, 1, 1);
//				axis.getUnitVector().timesScalar(currentMag).render(gApp, x, y);
//				gApp.stroke(1, 255, 1);
//			}
//			
//			if (Math.abs(currentMag) < Math.abs(MTVMag)) {
//				MTVMag = currentMag;
//				MTV = axis.getUnitVector();
//				usedAxis= axis;
//			}
//
//			//			if (this == this.ref.players.get(0)) MTV.timesScalar(MTVMag).render(gApp, xPos, yPos);;
//		}
//
//		if (this.cScheme != null) MTV.timesScalar(MTVMag).render(gApp, x, y);
//
//
//		return MTV.timesScalar(MTVMag);
//	}
	
	
	//	public void removeCollision(Polygon other) {
	//		Vector MTV = this.getMTV(other);
	//
	//		if (this == this.ref.players.get(0)) this.translate(MTV.x, MTV.y);
	//	}

	@Override
	public void render(GameApplet gApp) {
		gApp.pushMatrix();

		//		for(Player p : this.ref.players) {
		//			if (this.collidingWith((Polygon) p)) {
		//				gApp.stroke(255, 1, 1);
		//			}
		//		}
		gApp.translate(x, y);
		gApp.beginShape();
		for (Point point : this.points) {
			gApp.vertex(point.x, point.y);
		}
		gApp.endShape(gApp.CLOSE);
		gApp.popMatrix();

	}


	@Override
	protected List<Vector> getCollisionAxis(Shape other) {
		return getPerpVectors(this.toVectors());
	}


}
