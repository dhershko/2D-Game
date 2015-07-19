package DefaultHelpers;

import java.util.ArrayList;
import java.util.List;

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
		// Controlled Points
		for(int i = 0; i < 0; i++) {
;
		}
		// Points
		for(int i = 0; i < 0; i++) {

		}

		// Lines
		for(int i = 0; i < 0; i++) {

		}

		// Circles
		for(int i = 0; i < 0; i++) {

		}

		// Big square
//		List<Point> big = new ArrayList<Point>() {
//			add(new Point(200 + 100, 200 + 100));
//			add(new Point(200 + 100, 200 - 100));
//			add(new Point(200 - 100, 200 - 100));
//			add(new Point(200 - 100, 200 + 100));
//		}};
		
		// Compound shape of circle and poly
		for(int i = 0; i < 1; i++) {
			addCompoundSprite(ref);
		}
	}

	private static void addControlledPolySprite(SpriteReferee ref) {
		Polygon poly = new Polygon(new Point(150,150),  5, 20);
		String[] keyWords = new String[]{PropertyKeyWords.COLLIDABLE, PropertyKeyWords.ROUNDSCENE, PropertyKeyWords.CONTROLLED}; 
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
}
