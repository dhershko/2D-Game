package shapes;

import java.math.BigDecimal;

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
		s.vel = s.vel.add(linearChange);

		Vector impulseVec = this.getImpulseVec();

		Vector ra = pointOfApp.getThisPointRelativeTo(s.position).toVector();





		double ia = s.getInertia();
		double rotVelChange = ra.crossProduct(impulseVec)/ia;


		this.s.rotationalVel += rotVelChange;//*.017453;//to convert to radians from degrees

		// Debug for controlled shape
		if (this.s.cScheme != null) {
			System.out.println("LinearChange: " + linearChange);
		}
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
		Double CORd = s.coefficentOfRestitution(oS);

		Double velChangeScalard = s.mass*oS.mass*(CORd+1)/(s.mass+oS.mass);

		Vector toReturnd = this.oSVelProj.minus(this.sVelProj).timesScalar(velChangeScalard);	
		toReturnd = toReturnd.timesScalar(1.0/s.mass);
		
//		BigDecimal sMass = BigDecimal.valueOf(s.mass);
//		BigDecimal oSMass = BigDecimal.valueOf(oS.mass);
//		BigDecimal COR = BigDecimal.valueOf(s.coefficentOfRestitution(oS));
//
//		BigDecimal velChangeScalar = sMass.multiply(oSMass).multiply(COR.add(BigDecimal.ONE))
//				.divide(sMass.add(oSMass), BigDecimal.ROUND_DOWN);
//		
//
//
//		BigDecimal osVelProjX = BigDecimal.valueOf(this.oSVelProj.x);
//		BigDecimal osVelProjY = BigDecimal.valueOf(this.oSVelProj.y);
//		BigDecimal sVelProjX = BigDecimal.valueOf(this.sVelProj.x);
//		BigDecimal sVelProjY = BigDecimal.valueOf(this.sVelProj.y);
//
//		BigDecimal toReturnX = osVelProjX.subtract(sVelProjX).multiply(velChangeScalar).divide(sMass, BigDecimal.ROUND_DOWN);
//		BigDecimal toReturnY = osVelProjY.subtract(sVelProjY).multiply(velChangeScalar).divide(sMass, BigDecimal.ROUND_DOWN);
//		
////		System.out.println("BigDec: " + velChangeScalar);
////		System.out.println("Doub: " + velChangeScalard);
//
//		Vector toReturn = new Vector(toReturnX.doubleValue(), toReturnY.doubleValue());
//		
//		return toReturn;


//
		return toReturnd; 
	}

	public double getAngularComponent(Vector linearComponent) {
		Vector ra = pointOfApp.getThisPointRelativeTo(s.position).toVector();
		return ra.crossProduct(linearComponent);

	}

}
