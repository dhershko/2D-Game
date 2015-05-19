package polygons;

import gameReferee.GameApplet;
import processing.core.PApplet;

public class PolygonTests extends GameApplet {
	public void setup() {
		size(640, 360);
		background(255);

	}

	public void draw() {
		Point p1 = new Point(150, 150);
		Point p2 = new Point(200, 350);
		Line l1 = new Line(p1, p2);
		
		Vector v1 = new Vector(50, 0);
		
		Vector perpV1 = v1.getPerpVector();
		
		v1.render(this, 150, 150);
		
		perpV1.render(this, 150, 150);
		

	} 

	public static void main(String [] args) {
		
		PApplet.main(new String[] { "--present", PolygonTests.class.getName() });
	}
}
