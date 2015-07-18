package EventHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import EventHandling.eventHandlerFunctions.beyondSceneEventHandlerFunctions.BeyondSceneEventHandlerFunction;
import EventHandling.eventHandlerFunctions.collisionEventHandlerFunctions.CollisionEventHandlerFunction;
import EventHandling.events.CollisionEvent;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public abstract class EventHandler {
	public static String KEYWORDDELINEATOR = ",";

	private HashMap<String, List<CollisionEventHandlerFunction>> collisionToFunctionMapping;
	private HashMap<String, List<BeyondSceneEventHandlerFunction>> beyondSceneToFunctionMapping;
	
	/**
	 * If a property is present in both objects right now, will only call the function once.
	 */
		public EventHandler() {
			this.collisionToFunctionMapping = new HashMap<String, List<CollisionEventHandlerFunction>>();
			this.beyondSceneToFunctionMapping = new HashMap<String, List<BeyondSceneEventHandlerFunction>>();
		}
	
	
	// -----------HANDLE OFFSCENE EVENTS-----------
	public void handleOffSceneEvent(BeyondSceneEvent event) {
		// Collided with itself an off the map collision
		List<BeyondSceneEventHandlerFunction>	functionsToCall = getAllOffSceneFunctionsToCall(event.s1.getCollisionName());
		for (BeyondSceneEventHandlerFunction function : functionsToCall) {
			function.handleCollisionEvent(event);
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
		// A generic collision
		functionsToCall = getAllFunctionsToCall(event.s1.getCollisionName(), event.s2.getCollisionName());
		for (CollisionEventHandlerFunction function : functionsToCall) {
			function.handleCollisionEvent(event);
		}
	}

	private List<CollisionEventHandlerFunction> getAllFunctionsToCall(String collisionName1, String collisionName2) {
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


}
