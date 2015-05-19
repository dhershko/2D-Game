//package gameObjects;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import gameActions.GameAction;
//import gameActions.MoveAction;
//import gameReferee.GameApplet;
//import gameReferee.Referee;
//
//public class Projectile extends DynamicObject{
//
//	public Projectile(Referee ref, GameApplet gApp, float xPos, float yPos,
//			float xVel, float yVel, float xAcc, float yAcc) {
//		super(ref, gApp, xPos, yPos, xVel, yVel, xAcc, yAcc);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public
//	List<GameAction> getActions() {
//		List<GameAction> toReturn = new ArrayList<GameAction>();
//		toReturn.add(new MoveAction(this));
//		return toReturn;
//	}
//
//	@Override
//	public void render() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
