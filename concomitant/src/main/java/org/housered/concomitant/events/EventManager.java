package org.housered.concomitant.events;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.housered.concomitant.conditions.Event;

public class EventManager {
    
    private Set<Event> eventsOccurred = Collections.synchronizedSet(new HashSet<Event>());

    public boolean hasEventOccurred(Event event) {
        return eventsOccurred.contains(event);
    }

    public void announce(Event event) {
        eventsOccurred.add(event);
    }
    
}
