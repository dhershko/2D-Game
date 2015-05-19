package gameActions;

import java.util.HashMap;

import gameReferee.Referee;

public  class GameActionHelpers {
	
//	public static GameAction keyToAction(char key, Referee ref){
//		
//		Player playerToUse = ref.players.get(0);
//		
//		
//		switch(key) {
//		case 'd':
//			return new MoveRightAction(playerToUse);
//		case 'a':
//			return new MoveLeftAction(playerToUse);
//		case 'w':
//			return new MoveUpAction(playerToUse);
//		case 's':
//			return new MoveDownAction(playerToUse);
//		}
//		
//		return null;
//	}
//	
	public static ControlScheme getWASDMovementScheme() {
		HashMap<Character, GameAction> HM = new HashMap<Character, GameAction>();
		
		HM.put('d', new MoveRightAction());
		HM.put('s', new MoveDownAction());
		HM.put('w', new MoveUpAction());
		HM.put('a', new MoveLeftAction());
		return new ControlScheme(HM);
		
	}
}
