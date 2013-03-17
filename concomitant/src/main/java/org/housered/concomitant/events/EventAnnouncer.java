package org.housered.concomitant.events;

import org.housered.concomitant.conditions.Event;


public class EventAnnouncer {
    
    private EventManager eventManager;
    private Event event;

    public EventAnnouncer(Event event, EventManager eventManager) {
        this.event = event;
        this.eventManager = eventManager;
    }

    public <T> T on(T object) {
        // Return a mock that performs the called actions on this given
        // object, but carefully wrapped, to check any other conditions that
        // might've changed state, and confirm the preceeding and subsequent changes.
        
        return object;
    }

    public void now() {
        eventManager.announce(event);
    }
    
}
