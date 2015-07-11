package gameReferee;

import menu.MenuReferee;

public class Game extends GameApplet {

	@Override
	public Referee getInitialRef() {
		PhysicsReferee ref = new PhysicsReferee();
		ref.initializeMap(this);
		return ref;
	}
	
	public static void main(String [] args) {
		GameApplet g = new Game();
		g.runGame();
	}
}
