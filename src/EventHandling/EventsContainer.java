package EventHandling;

import java.util.ArrayList;
import java.util.List;

import EventHandling.events.CollisionEvent;
import EventHandling.events.beyondSceneEvents.BeyondSceneEvent;

public class EventsContainer {
	private List<CollisionEvent> collisionEvents;
	private List<BeyondSceneEvent> beyondSceneEvents;
	
	public EventsContainer() {
		this.collisionEvents = new ArrayList<CollisionEvent>();
		this.beyondSceneEvents = new ArrayList<BeyondSceneEvent>();
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
	
	

}
