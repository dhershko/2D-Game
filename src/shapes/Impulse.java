package shapes;

import java.math.BigDecimal;

import gameObjects.Sprite;
import geometryHelp.Line;
import geometryHelp.Point;
import geometryHelp.Vector;

public class Impulse {

	private Point pointOfApp;
	private Vector MTV;
	private Sprite s;
	private Sprite oS;
	private Line sProj;
	private Line oSProj;
	private Vector sVelProj;
	private Vector oSVelProj;
	private double oRotVel;
	private double thisRotVel;

	public Impulse(Point pointOfApp, Vector MTV, Sprite s, Sprite oS) {
		this.pointOfApp = pointOfApp;
		this.MTV = MTV;
		this.s = s;
		this.oS = oS;
		this.sProj = s.hitBox.projectOntoVector(MTV);
		this.oSProj = oS.hitBox.projectOntoVector(MTV);
		this.sVelProj = getVelocityProjection(MTV, s.vel, s.rotationalVel, pointOfApp, s.hitBox.getCentroid());
		this.oSVelProj = getVelocityProjection(MTV, oS.vel, s.rotationalVel, pointOfApp, oS.hitBox.getCentroid());
		this.oRotVel = oS.rotationalVel;
		this.thisRotVel = thisRotVel;
	}
	
	private static Vector getVelocityProjection(Vector MTV, Vector vel, double rotVel, Point pointOfApp, Point center) {
//		Vector radius = pointOfApp.getThisPointRelativeTo(center).toVector();
//		Vector rotVect = radius.getPerpVector().timesScalar(rotVel);
//		Vector totalVel = vel.add(rotVect);
//		
//		return totalVel.getProjection(MTV);
		return vel.getProjection(MTV);
	}



	public void executeImpulse() {		
		// Apply linear
		Vector linearChange = getLinearComponent();
		s.vel = s.vel.add(linearChange);

		// Apply rotation
		Vector impulseVec = this.getImpulseVec();
		Vector ra = pointOfApp.getThisPointRelativeTo(s.hitBox.position).toVector();
		double ia = s.hitBox.getInertia();
		double rotVelChange = ra.crossProduct(impulseVec)/ia;
		this.s.rotationalVel += rotVelChange;//*.017453;//to convert to radians from degrees
	}

	public Vector getVectorOfApplicationRelativeToCentroid(Shape s) {
		Point cen = s.position;
		return pointOfApp.getThisPointRelativeTo(cen).toVector();
	}

	public double getChangeInRot() {

		Vector ra = pointOfApp.getThisPointRelativeTo(s.hitBox.position).toVector();
		Vector rb =  pointOfApp.getThisPointRelativeTo(oS.hitBox.position).toVector();

		double ia = s.hitBox.getInertia();
		double ib = oS.hitBox.getInertia();

		Double COR = s.coefficentOfRestitution(oS);

		Vector MTVNorm = MTV.getUnitVector();

		double iMag = -(1+COR)/(1/s.hitBox.getMass()+1/s.hitBox.getMass()+
				(Math.pow(ra.getPerpVector().dotProduct(MTVNorm), 2))/ia+
				(Math.pow(rb.getPerpVector().dotProduct(MTVNorm), 2))/ib);


		Vector impulseVec =	this.sVelProj.minus(this.oSVelProj).timesScalar(iMag);

		return ra.crossProduct(impulseVec)/ia;
	}

	public Vector getImpulseVec() {
		Vector ra = pointOfApp.getThisPointRelativeTo(s.hitBox.position).toVector();
		Vector rb =  pointOfApp.getThisPointRelativeTo(oS.hitBox.position).toVector();

		double ia = s.hitBox.getInertia();
		double ib = oS.hitBox.getInertia();

		Double COR = s.coefficentOfRestitution(oS);

		Vector MTVNorm = MTV.getUnitVector();

		double iMag = -(1+COR)/(1/s.hitBox.getMass()+1/oS.hitBox.getMass()+
				(Math.pow(ra.getPerpVector().dotProduct(MTVNorm), 2))/ia+
				(Math.pow(rb.getPerpVector().dotProduct(MTVNorm), 2))/ib);


		return	this.sVelProj.minus(this.oSVelProj).timesScalar(iMag);

	}

	public Vector getLinearComponent() {
		Double CORd = s.coefficentOfRestitution(oS);
		double sMass = s.hitBox.getMass();
		double osMass = oS.hitBox.getMass();

		Double velChangeScalard = sMass*osMass*(CORd+1)/(sMass+osMass);

		Vector toReturnd = this.oSVelProj.minus(this.sVelProj).timesScalar(velChangeScalard);	
		toReturnd = toReturnd.timesScalar(1.0/sMass);
		
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
		Vector ra = pointOfApp.getThisPointRelativeTo(s.hitBox.position).toVector();
		return ra.crossProduct(linearComponent);
	}
	
	@Override
	public String toString() {
		return "Impulse with linear component: " + this.getLinearComponent();
	}

}
