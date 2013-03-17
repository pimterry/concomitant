package org.housered.concomitant.conditions;

public class WaitCondition implements Condition {
    
    private long duration;
    private Long startTime;

    public WaitCondition(long duration) {
        this.duration = duration;
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean satisfied() {
        if (startTime == null) {
            return false;
        } else {
            return System.currentTimeMillis() - startTime >= duration;
        }
    }

}
