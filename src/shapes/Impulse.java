package shapes;

import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

public class Impulse {

	Point pointOfApp;
	Vector MTV;
	Shape s;
	Shape oS;
	Line sProj;
	Line oSProj;
	Vector sVelProj;
	Vector oSVelProj;
	double oRotVel;
	double thisRotVel;

	public Impulse(Point pointOfApp, Vector MTV, Shape s, Shape oS) {
		this.pointOfApp = pointOfApp;
		this.MTV = MTV;
		this.s = s;
		this.oS = oS;
		this.sProj = s.projectOntoVector(MTV);
		this.oSProj = oS.projectOntoVector(MTV);
		this.sVelProj = this.s.vel.getProjection(MTV);
		this.oSVelProj = this.oS.vel.getProjection(MTV);
		this.oRotVel = oS.rotationalVel;
		this.thisRotVel = thisRotVel;
	}



	public void executeImpulse() {		

		Vector linearChange = getLinearComponent();
		s.vel = s.vel.add(linearChange).timesScalar(1.0/s.mass);
		//		double changeInRot = this.getChangeInRot();
		//		this.s.rotationalVel += changeInRot;


		//TODO: THIS IS CAUSING IT TO BREAK

		Vector impulseVec = this.getImpulseVec();

		//		s.vel = s.vel.add(impulseVec.getProjection(MTV));

		Vector ra = pointOfApp.getThisPointRelativeTo(s.position).toVector();

		
		
		
		
		double ia = s.getInertia();
		double rotVelChange = ra.crossProduct(impulseVec)/ia;
		
		
		this.s.rotationalVel += rotVelChange*.017453;//to convert to radians from degrees



		//		impulseVec.render(s.gApp, (float) this.pointOfApp.x, (float)this.pointOfApp.y);

	}


	public Vector getVectorOfApplicationRelativeToCentroid(Shape s) {
		Point cen = s.position;
		return pointOfApp.getThisPointRelativeTo(cen).toVector();
	}

	public double getChangeInRot() {

		Vector ra = pointOfApp.getThisPointRelativeTo(s.position).toVector();
		Vector rb =  pointOfApp.getThisPointRelativeTo(oS.position).toVector();

		double ia = s.getInertia();
		double ib = oS.getInertia();

		Double COR = s.coefficentOfRestitution(oS);

		Vector MTVNorm = MTV.getUnitVector();

		double iMag = -(1+COR)/(1/s.mass+1/s.mass+
				(Math.pow(ra.getPerpVector().dotProduct(MTVNorm), 2))/ia+
				(Math.pow(rb.getPerpVector().dotProduct(MTVNorm), 2))/ib);


		Vector impulseVec =	this.sVelProj.minus(this.oSVelProj).timesScalar(iMag);

		return ra.crossProduct(impulseVec)/ia;
	}

	public Vector getImpulseVec() {
		Vector ra = pointOfApp.getThisPointRelativeTo(s.position).toVector();
		Vector rb =  pointOfApp.getThisPointRelativeTo(oS.position).toVector();

		double ia = s.getInertia();
		double ib = oS.getInertia();

		Double COR = s.coefficentOfRestitution(oS);

		Vector MTVNorm = MTV.getUnitVector();

		double iMag = -(1+COR)/(1/s.mass+1/oS.mass+
				(Math.pow(ra.getPerpVector().dotProduct(MTVNorm), 2))/ia+
				(Math.pow(rb.getPerpVector().dotProduct(MTVNorm), 2))/ib);


		return	this.sVelProj.minus(this.oSVelProj).timesScalar(iMag);

	}

	public Vector getLinearComponent() {
		Double COR = s.coefficentOfRestitution(oS);

		Double velChangeScalar = s.mass*oS.mass*(COR+1)/(s.mass+oS.mass);
		
		return this.oSVelProj.minus(this.sVelProj).timesScalar(velChangeScalar);	
	}
	
	public double getAngularComponent(Vector linearComponent) {
		Vector ra = pointOfApp.getThisPointRelativeTo(s.position).toVector();
		return ra.crossProduct(linearComponent);
		
	}

}
