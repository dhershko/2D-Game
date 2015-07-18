package gameReferee;

import geometryHelp.Line;
import geometryHelp.Point;

import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import topLevel.Renderer;

public class PAppletRenderer extends PApplet implements Renderer {
	

	@Override
	public void rCircle(Point center, double radius) {
		this.ellipse((float)center.x, (float)center.y, (float)radius, (float)radius);
	}

	@Override
	public void rLine(Line toRender) {
		this.line((float)toRender.p1.x, (float)toRender.p1.y, (float)toRender.p2.x, (float)toRender.p2.y);
	}

	@Override
	public void rPoint(Point point) {
		this.point((float)point.x, (float)point.y);
	}

	@Override
	public void rText(String input, double vertOffset, double textHeight) {
		fill(50);
		this.textAlign(PApplet.CENTER);
		this.textFont(this.createFont("Arial",(float)textHeight,true));
		this.text(input, (float) (this.width/2.0), (float) vertOffset);
	}


	PImage polyImage = loadImage("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSmjOJ3ZnNbK7mmYbirShpZtDgiB8JHUxg-J_HbwtqcmS2gvcVFTJXFUg", "png");

	@Override
	public void rPolygon(List<Point> vertices) {

		polyImage.resize(WIDTH, HEIGHT);
		this.beginShape();
		this.texture(polyImage);
		this.textureMode(NORMAL);
		for (Point point : vertices) {
			float mappedX = PApplet.map((float)point.x, 0, this.width, 0, 1);
			float mappedY = PApplet.map((float)point.y, 0, this.width, 0, 1);
			this.vertex((float)point.x, (float)point.y, mappedX, mappedY);
		}
		this.endShape(PApplet.CLOSE);
	}

}
