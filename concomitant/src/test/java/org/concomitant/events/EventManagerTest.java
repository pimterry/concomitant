package org.concomitant.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.concomitant.conditions.Event;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EventManagerTest {

    private ExpectedException exception = ExpectedException.none();
    
    private EventManager events = new EventManager();
    private Random r = new Random();
    
    @Test
    public void shouldSayEventsHaveNotAlreadyOccurredInitially() {
        assertFalse(events.hasEventOccurred(randomEvent()));
    }
    
    @Test
    public void shouldThrowNullPointersForNullEvents() {
        exception.expect(NullPointerException.class);
        events.hasEventOccurred(null);
    }
    
    @Test
    public void shouldSayEventsHaveOccurredOnceAnnounced() {
        Event event = randomEvent();
        
        events.announce(event);
        assertTrue(events.hasEventOccurred(event));
    }
    
    private Event randomEvent() {
        String name = String.valueOf(r.nextLong());
        return new Event(name);
    }

}
