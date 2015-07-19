package EventHandling;

import java.util.ArrayList;
import java.util.List;

import EventHandling.events.ActionEvent;
import EventHandling.events.CollisionEvent;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public class EventsContainer {
	private List<CollisionEvent> collisionEvents;
	private List<BeyondSceneEvent> beyondSceneEvents;
	private List<ActionEvent> actionEvents;
	
	public EventsContainer() {
		this.collisionEvents = new ArrayList<CollisionEvent>();
		this.beyondSceneEvents = new ArrayList<BeyondSceneEvent>();
		this.actionEvents = new ArrayList<ActionEvent>();
	}
	
	// Collision events.
	public List<CollisionEvent> getCollisionEvents() {
		return this.collisionEvents;
	}
	public void addCollisionEvents(List<CollisionEvent> cEvents) {
		this.collisionEvents.addAll(cEvents);
	}
	
	public void addCollisionEvent(CollisionEvent cEvent) {
		this.collisionEvents.add(cEvent);
	}
	
	// Beyond scene events.
	public List<BeyondSceneEvent> getBeyondSceneEvents() {
		return this.beyondSceneEvents;
	}
	
	public void addBeyondSceneEvents(List<BeyondSceneEvent> bsE) {
		this.beyondSceneEvents.addAll(bsE);
	}
	public void addBeyondSceneEvent(BeyondSceneEvent bsE) {
		this.beyondSceneEvents.add(bsE);
	}
	
	// Action events.
		public List<ActionEvent> getActionEvents() {
		return this.actionEvents;
	}
	
	public void addActionEvents(List<ActionEvent> bsE) {
		this.actionEvents.addAll(bsE);
	}
	public void addActionEvent(ActionEvent bsE) {
		this.actionEvents.add(bsE);
	}
	

}
