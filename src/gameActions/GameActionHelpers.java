package gameActions;

import java.util.HashMap;

import gameReferee.Referee;

public  class GameActionHelpers {

	public static ControlScheme getWASDMovementScheme() {
		HashMap<Character, GameAction> HM = new HashMap<Character, GameAction>();
		
		HM.put('d', new MoveRightAction());
		HM.put('s', new MoveDownAction());
		HM.put('w', new MoveUpAction());
		HM.put('a', new MoveLeftAction());
		HM.put('e', new RotateC());
		HM.put('q', new RotateCC());
		return new ControlScheme(HM);
		
	}
}
