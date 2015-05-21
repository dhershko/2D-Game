package polygons;

public class Impulse {

	Point pointOfApp;
	Vector MTV;
	Shape s;
	Shape oS;
	Line sProj;
	Line oSProj;
	Vector sVelProj;
	Vector oSVelProj;
	
	public Impulse(Point pointOfApp, Vector MTV, Shape s, Shape oS) {
		this.pointOfApp = pointOfApp;
		this.MTV = MTV;
		this.s = s;
		this.oS = oS;
		this.sProj = s.projectOntoVector(MTV);
		this.oSProj = oS.projectOntoVector(MTV);
		this.sVelProj = this.s.vel.getProjection(MTV);
		this.oSVelProj = this.oS.vel.getProjection(MTV);
		
	}
	
	public void executeImpulse() {
		Vector velChange = getChangeInVel();
		s.vel = s.vel.add(velChange).timesScalar(1.0/s.mass);
		double changeInRot = this.getChangeInRot();
		this.s.rotationalVel += changeInRot;
	}
	
	
	public Vector getVectorOfApplicationRelativeToCentroid(Shape s) {
		Point cen = s.getCentroid();
		return pointOfApp.getThisPointRelativeTo(cen).toVector();
	}
	
	public double getChangeInRot() {
		
		Vector ra = pointOfApp.getThisPointRelativeTo(s.getCentroid()).toVector();
		Vector rb =  pointOfApp.getThisPointRelativeTo(oS.getCentroid()).toVector();
		
		double ia = s.getInertia();
		double ib = oS.getInertia();
		
		Double COR = s.coefficentOfRestitution(oS);
		
		Vector MTVNorm = MTV.getUnitVector();
		
		double iMag = -(1+COR)/(1/s.mass+1/s.mass+
				(Math.pow(ra.getPerpVector().dotProduct(MTVNorm), 2))/ia+
				(Math.pow(rb.getPerpVector().dotProduct(MTVNorm), 2))/ib);

		
		Vector impulseVec =	this.sVelProj.minus(this.oSVelProj).timesScalar(iMag);

//		if (s.cScheme!= null) {
//			impulseVec.timesScalar(50).render(s.gApp,(float) this.pointOfApp.x, (float) this.pointOfApp.y);
//			s.gApp.stroke(255,1,1);
//			ra.render(s.gApp, (float)s.position.x, (float)s.position.y);
//			s.gApp.stroke(1,255,1);
//		}
//		
		return ra.times(impulseVec)/ia;
		
	}
	
public Vector getChangeInVel() {

	Double COR = s.coefficentOfRestitution(oS);

	Double velChangeScalar = s.mass*oS.mass*(1+COR)/(s.mass+oS.mass);

	return this.oSVelProj.minus(this.sVelProj).timesScalar(velChangeScalar);

		
	}
	
}
