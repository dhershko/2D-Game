package DefaultHelpers;

import gameActions.GameActionHelpers;
import gameObjects.Sprite;
import gameReferee.SpriteReferee;
import geometryHelp.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import EventHandling.EventHandler;
import EventHandling.PropertyKeyWords;
import shapes.Polygon;

public class SpriteRefHelpers {
	public static void getExample(SpriteReferee ref) {
		// Generic polygons
		for(int i = 0; i < 15; i++) {
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
	}

	private static void addControlledPolySprite(SpriteReferee ref) {
		Polygon poly = new Polygon(new Point(150,150),  5, 20);
		String keyWords = PropertyKeyWords.COLLIDABLE + EventHandler.KEYWORDDELINEATOR + PropertyKeyWords.ROUNDSCENE; 
		new Sprite(keyWords, poly,ref, GameActionHelpers.getWASDMovementScheme());
		
	}

	public static void addGenericPolySprite(SpriteReferee ref) {
		Polygon poly = new Polygon(new Point(300,300),  5, 20);
		String keyWords = PropertyKeyWords.COLLIDABLE + EventHandler.KEYWORDDELINEATOR + PropertyKeyWords.ROUNDSCENE; 
		new Sprite(keyWords, poly,ref, null);
	}
}
