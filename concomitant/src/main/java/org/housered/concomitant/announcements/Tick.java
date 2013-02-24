package org.housered.concomitant.announcements;

import org.housered.concomitant.Condition;


public class Tick implements Condition {

    public Tick(int tickNum) {
    }

    /**
     * Returns true only once the tick that this tick represents has been reached.
     */
    @Override
    public boolean isTrue() {
        return false;
    }

}
