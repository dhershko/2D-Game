package geometryHelp;

public class Interval {

	public double min;
	public double max;
	
	public Interval(Line line, Vector vec) {
		double firstPointDistnance = line.p1.distanceAlongVector(vec);
		double secondPointDistance = line.p2.distanceAlongVector(vec);
		
		this.min = Math.min(firstPointDistnance, secondPointDistance);
		this.max = Math.max(firstPointDistnance, secondPointDistance);
	}
	
	public Interval(double min, double max) {
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
	}
	
	public Double getMTV(Interval other) {
		double thisRight = other.max - this.min;
		double thisLeft = this.max - other.min;
		 if (thisLeft <= 0 || thisRight <= 0 )
		    return null;
		  if (thisRight < thisLeft) {
		    return thisRight;
		  }
		   return -thisLeft;	
	}
	
	@Override
	public String toString() {
		return "Interval from " + this.min + " to " + this.max;
	}
	
}
