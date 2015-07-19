package DefaultHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameObjects.Sprite;
import gameReferee.SpriteReferee;
import geometryHelp.Point;
import EventHandling.EventHandler;
import shapes.Circle;
import shapes.CompoundShape;
import shapes.Polygon;
import shapes.Shape;

public class SpriteRefHelpers {
	public static void getExample(SpriteReferee ref) {

		// Generic polygons
		for(int i = 0; i < 0; i++) {
			addGenericPolySprite(ref);
		}

		// Controled polygons
		for(int i = 0; i < 1; i++) {
			addControlledPolySprite(ref);
		}

		// Circles
		for(int i = 0; i < 0; i++) {
			addCircleSprite(ref);
		}

		// Rectangles
		for (int i = 0; i < 4; i++) {
			addRectangle(ref, i);
		}
		
		// Compound shape of circle and poly
		for(int i = 0; i < 0; i++) {
			addCompoundSprite(ref);
		}
	}

	private static void addControlledPolySprite(SpriteReferee ref) {
		Polygon poly = new Polygon(new Point(200,300),  5, 20);
		String[] keyWords = new String[]{PropertyKeyWords.COLLIDABLE, PropertyKeyWords.ROUNDSCENE, PropertyKeyWords.CONTROLLED, PropertyKeyWords.COLLIDABLE2}; 
		new Sprite(keyWords, poly,ref);
		
	}

	public static void addGenericPolySprite(SpriteReferee ref) {
		Polygon poly = new Polygon(new Point(300,300),  5, 20);
		String[] keyWords = new String[]{PropertyKeyWords.COLLIDABLE, PropertyKeyWords.ROUNDSCENE}; 
		new Sprite(keyWords,poly,ref);
	}
	
	public static void addCompoundSprite(SpriteReferee ref) {
		List<Shape> allShapes = new ArrayList<Shape>();
		Polygon poly = new Polygon(new Point(300,300),  5, 20);
		allShapes.add(poly);
		Circle circ = new Circle(new Point(300, 280), 20);
		allShapes.add(circ);
		String[] keyWords = new String[]{PropertyKeyWords.COLLIDABLE, PropertyKeyWords.ROUNDSCENE}; 
		CompoundShape cShape = new CompoundShape(allShapes);
		new Sprite(keyWords, cShape, ref);
	}
	
	public static void addCircleSprite(SpriteReferee ref) {
		Circle circ = new Circle(new Point(400,300), 20);
		String[] keyWords = new String[]{PropertyKeyWords.COLLIDABLE2, PropertyKeyWords.ROUNDSCENE}; 
		Sprite added = new Sprite(keyWords,circ,ref);
		added.vel.x -= 3;
	}
	
	public static void addRectangle(SpriteReferee ref, int i) {
		Random rand = new Random();
		List<Point> rectPs = new ArrayList<Point>();
		int xOffSet = rand.nextInt(10);
		int yOffSet = 40*i;
		int rectWidth = 30;
		rectPs.add(new Point(xOffSet+100,yOffSet));
		rectPs.add(new Point(xOffSet+100,yOffSet + rectWidth));
		rectPs.add(new Point(xOffSet+300, yOffSet + rectWidth));
		rectPs.add(new Point(xOffSet+300, yOffSet));
		Polygon rect = new Polygon(rectPs);
		String[] keyWords = new String[]{PropertyKeyWords.COLLIDABLE, PropertyKeyWords.ROUNDSCENE}; 
		Sprite added = new Sprite(keyWords, rect, ref);
	}
}
