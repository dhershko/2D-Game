package gameReferee;

import topLevel.Renderer;

public abstract class Referee {
	private Referee nextRef;
	
	public abstract void timeStep(GameApplet gApp);

	public abstract void render(Renderer rend);
	
	public Referee() {
		this.nextRef = this;
	}
	
	public Referee getNextRef() {
		return this.nextRef;
	}
	public void setNextRef(Referee nextRef) {
		this.nextRef = nextRef;
	}
}
