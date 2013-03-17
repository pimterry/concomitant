package org.housered.concomitant.conditions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void shouldRejectNullEventNames() {
        exception.expect(NullPointerException.class);
        new Event(null);
    }
    
    @Test
    public void shouldConsiderEventsWithMatchingNamesAsEqual() {
        String name = "event-name";
        Event a = new Event(name);
        Event b = new Event(name);
        
        assertEquals(a, b);
    }
    
    @Test
    public void shouldConsiderEventsWithDifferentNamesAsInequal() {
        Event a = new Event("a name");
        Event b = new Event("another name");
        
        assertNotEquals(a, b);
    }

}
