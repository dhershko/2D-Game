package EventHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import EventHandling.eventHandlerFunctions.actionHandlerFunctions.ActionEventHandlerFunction;
import EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions.BeyondSceneEventHandlerFunction;
import EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions.CollisionEventHandlerFunction;
import EventHandling.events.ActionEvent;
import EventHandling.events.CollisionEvent;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public abstract class EventHandler {
	public static String KEYWORDDELINEATOR = ",";

	private HashMap<String, List<CollisionEventHandlerFunction>> collisionToFunctionMapping;
	private HashMap<String, List<BeyondSceneEventHandlerFunction>> beyondSceneToFunctionMapping;
	private HashMap<ActionEventHandlerKey, List<ActionEventHandlerFunction>> actionToFunctionMapping;
	
	/**
	 * If a property is present in both objects right now, will only call the function once.
	 */
		public EventHandler() {
			this.collisionToFunctionMapping = new HashMap<String, List<CollisionEventHandlerFunction>>();
			this.beyondSceneToFunctionMapping = new HashMap<String, List<BeyondSceneEventHandlerFunction>>();
			this.actionToFunctionMapping = new HashMap<ActionEventHandlerKey, List<ActionEventHandlerFunction>>();
		}
	
	
	// -----------HANDLE OFFSCENE EVENTS-----------
	public void handleOffSceneEvent(BeyondSceneEvent event) {
		// Collided with itself an off the map collision
		List<BeyondSceneEventHandlerFunction>	functionsToCall = getAllOffSceneFunctionsToCall(event.beyondScene.getProperties());
		for (BeyondSceneEventHandlerFunction function : functionsToCall) {
			function.handleBeyondSceneEvent(event);
		}

	}
	private List<BeyondSceneEventHandlerFunction> getAllOffSceneFunctionsToCall(String collisionName1) {
		List<BeyondSceneEventHandlerFunction> toReturn = new ArrayList<BeyondSceneEventHandlerFunction>();
		for (String keyWord1 : collisionName1.split(KEYWORDDELINEATOR)) {
				List<BeyondSceneEventHandlerFunction> returned = this.beyondSceneToFunctionMapping.get(keyWord1);
				if (returned != null) { 
					toReturn.addAll(returned); 
				}	
		}
		return toReturn;
	}
	public void addOffSceneFunctionToCall(String keyWord1,  BeyondSceneEventHandlerFunction eventHandlerFun) {
		String key = keyWord1;
		List<BeyondSceneEventHandlerFunction> curr = this.beyondSceneToFunctionMapping.get(key);
		if (curr == null) {
			curr = new ArrayList<BeyondSceneEventHandlerFunction>();
		}
		curr.add(eventHandlerFun);
		this.beyondSceneToFunctionMapping.put(key, curr);
	}

	// -----------HANDLE COLLISION EVENTS-----------
	public void handleCollisionEvent(CollisionEvent event) {
		List<CollisionEventHandlerFunction> functionsToCall = null;
		functionsToCall = getAllCollisionFunctionsToCall(event.s1.getProperties(), event.s2.getProperties());
		for (CollisionEventHandlerFunction function : functionsToCall) {
			function.handleCollisionEvent(event);
		}
	}

	private List<CollisionEventHandlerFunction> getAllCollisionFunctionsToCall(String collisionName1, String collisionName2) {
		List<CollisionEventHandlerFunction> toReturn = new ArrayList<CollisionEventHandlerFunction>();
		for (String keyWord1 : collisionName1.split(KEYWORDDELINEATOR)) {
			for (String keyWord2 : collisionName2.split(KEYWORDDELINEATOR)) {
				List<CollisionEventHandlerFunction> returned = this.collisionToFunctionMapping.get(keyWord1 + KEYWORDDELINEATOR + keyWord2);
				if (returned != null) { 
					toReturn.addAll(returned); 
				}	
				if (!keyWord1.equals(keyWord2)) {
					returned = this.collisionToFunctionMapping.get(keyWord2 + KEYWORDDELINEATOR + keyWord1);
					if (returned != null) { 
						toReturn.addAll(returned); 
					}
				}
			}
		}
		return toReturn;
	}

	public void addCollisionFunctionToCall(String keyWord1, String keyWord2, CollisionEventHandlerFunction eventHandler) {
		String key = keyWord1+ KEYWORDDELINEATOR + keyWord2;
		List<CollisionEventHandlerFunction> curr = this.collisionToFunctionMapping.get(key);
		if (curr == null) {
			curr = new ArrayList<CollisionEventHandlerFunction>();
		}
		curr.add(eventHandler);
		this.collisionToFunctionMapping.put(key, curr);
	}

	// -----------HANDLE ACTION EVENTS-----------
	private static class ActionEventHandlerKey {
		private String property;
		private Character pressedKey;

		ActionEventHandlerKey(String property, Character actionKeyPressed) {
			this.property = property;
			this.pressedKey = actionKeyPressed;
		}
		@Override
		public String toString() {
			return "ActionEventHandlerKey of key " + pressedKey + " and property " + this.property;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof ActionEventHandlerKey) {
				ActionEventHandlerKey oCast = ((ActionEventHandlerKey) o);
				return (this.property.equals(oCast.property) 
						&& this.pressedKey.equals(oCast.pressedKey));
			}
			return false;
		}
		@Override
		public int hashCode() {
			return this.pressedKey*22 + 13*this.property.length();
		}
	}
	
	
	public void handleActionEvent(ActionEvent event) {
		List<ActionEventHandlerFunction> functionsToCall = null;
		functionsToCall = getAllActionFunctionsToCall(event.currentSprite.getProperties(), event.pressedKeys);
		for (ActionEventHandlerFunction function : functionsToCall) {
			function.handleActionEvent(event.currentSprite);
		}
	}
	private List<ActionEventHandlerFunction> getAllActionFunctionsToCall(String spriteProperties, List<Character> pressedKeys) {
		List<ActionEventHandlerFunction> toReturn = new ArrayList<ActionEventHandlerFunction>();
		for (String property : spriteProperties.split(KEYWORDDELINEATOR)) {
			for (Character currChar : pressedKeys) {
				ActionEventHandlerKey key = new ActionEventHandlerKey(property, currChar);
				List<ActionEventHandlerFunction> returned = this.actionToFunctionMapping.get(key);
								if (returned != null) { 
					toReturn.addAll(returned); 
				}	
			}
		}
		return toReturn;
	}
	
	public void addActionEventHandler(String property, Character pressedKey, ActionEventHandlerFunction fun) {
		ActionEventHandlerKey key = new ActionEventHandlerKey(property, pressedKey);
		List<ActionEventHandlerFunction> curr = this.actionToFunctionMapping.get(key);
		if (curr == null) {
			curr = new ArrayList<ActionEventHandlerFunction>();
		}
		curr.add(fun);
		this.actionToFunctionMapping.put(key, curr);
	}
}
