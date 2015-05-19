package polygons;

import gameReferee.GameApplet;
import processing.core.PApplet;

public class Line {
	Point p1;
	Point p2;

	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		if (p1 == null || p2 == null)
			try {
				throw new NullPointForLineException();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
	}

	public Line(Vector vect1, Vector vect2) {
		this.p1 = new Point(vect1.x, vect1.y);
		this.p2 = new Point(vect2.x, vect2.y);
	}

	public Line projectOntoVector(Vector vec) {
		Vector v1 = new Vector(p1.x, p1.y);
		Vector v1Proj = v1.getProjection(vec);


		Vector v2 = new Vector(p2.x, p2.y);
		Vector v2Proj = v2.getProjection(vec);

		return new Line(v1Proj.toPoint(), v2Proj.toPoint());
	}
	
	public Double MTVScalar(Vector vec, Line otherParallelLine) {
		
		Interval thisInter = new Interval(this, vec);
		Interval thatInter = new Interval(otherParallelLine, vec);
		
		return thisInter.getMTV(thatInter);
	}

	public Point getFurtherLeftPoint(Vector vec) {
		Point furtherLeftPoint = p1;
		double p1Mag = p1.distanceAlongVector(vec);
		double p2Mag = p2.distanceAlongVector(vec);

		if (p2Mag < p1Mag) {
			furtherLeftPoint = p2;
		}
		return furtherLeftPoint;
	}

	public Point getFurtherRightPoint(Vector vec) {
		Point furtherRightPoint = p1;
		double p1Mag = p1.distanceAlongVector(vec);
		double p2Mag = p2.distanceAlongVector(vec);

		if (p2Mag > p1Mag) {
			furtherRightPoint = p2;
		}
		return furtherRightPoint;
	}



	/***
	 * Assumes lines are parallel and checks overlap along projection onto parallel axis
	 * @param otherParallelLine
	 * @return
	 */
	public boolean overlapsLineAlongAxis(Line otherParallelLine, Vector vec) {
		Point furtherLeft1 = this.getFurtherLeftPoint(vec);
		Point furtherRight1 = this.getFurtherRightPoint(vec);

		Point furtherLeft2 = otherParallelLine.getFurtherLeftPoint(vec);
		Point furtherRight2 = otherParallelLine.getFurtherRightPoint(vec);

		double min1 = furtherLeft1.distanceAlongVector(vec);
		double max1 = furtherRight1.distanceAlongVector(vec);
		double min2 = furtherLeft2.distanceAlongVector(vec);
		double max2 = furtherRight2.distanceAlongVector(vec);
				
		return min1 < max2 && min2 < max1;
	}

	public void render(GameApplet app) {
		app.pushMatrix();
		app.line(p1.x, p1.y, p2.x, p2.y);
		app.popMatrix();
	}

	public void render(GameApplet app, double xOffset, double yOffset) {
		app.pushMatrix();
		app.translate(xOffset, yOffset);
		app.line(p1.x, p1.y, p2.x, p2.y);
		app.popMatrix();
	}

	@Override
	public String toString() {
		return "Line: (" + p1 + ", " + p2 + ")";
	}
	
	public static void main (String[] args) {
		Point p1 = new Point(0, 0);
		Point p2 = new Point(-2, 2);
		Point p3 = new Point(-1, 1);
		Point p4 = new Point(-10, 10);
		Point p5 = new Point(1.99, 0);
		Point p6 = new Point(1, 1);

		
		Vector v1 = new Vector(-1, 1);
		Vector v2 = new Vector(1, 1);
		Vector v3 = new Vector(1, 0);
		
		Line l1 = new Line(p1, p2);
		Line l2 = new Line(p3, p4);
		Line l3 = new Line(p5, p6);
		
		
		
		System.out.println(l3.getFurtherLeftPoint(v2));
		System.out.println(l3.getFurtherRightPoint(v2));
		System.out.println(p5.distanceAlongVector(v2));
		System.out.println(p6.distanceAlongVector(v2));
		
		System.out.println(p5.toVector());
		System.out.println(p5.toVector().getProjection(v2));

		
	}
}
