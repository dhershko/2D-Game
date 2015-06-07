package gameReferee;

import processing.core.PApplet;

public class Game {
	public Referee ref;
	public GameApplet app;

	public Game() {
		this.app = new GameApplet();
		this.ref = new Referee(this.app);
	}

	public void runGame() {
		PApplet.main(new String[] { "--present", GameApplet.class.getName() });
	}


	public static void main(String [] args) {
		Game g = new Game();
		g.runGame();
		
	}
}
