package gameReferee;

public abstract class Referee {
	private Referee nextRef;
	
	public abstract void timeStep(GameApplet gApp);

	public abstract void render(GameApplet gApp);
	
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
