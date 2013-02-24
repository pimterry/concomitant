package org.housered.concomitant.announcements;


public abstract class Announcer {

    public <T> T on(T object) {
        // Return a mock that performs the called actions on this given
        // object, but carefully wrapped, to check any other conditions that
        // might've changed state, and confirm the preceeding and subsequent changes.
        
        return object;
    }
    
    protected abstract void announce();

    public void now() {
        announce();
    }
    
}
