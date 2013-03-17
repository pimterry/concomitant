package org.housered.concomitant.conditions;

import static org.junit.Assert.*;

import org.junit.Test;

public class WaitConditionTest {

    @Test
    public void shouldNotBeFulfilledBeforeStarted() {
        WaitCondition condition = new WaitCondition(1);
        assertFalse(condition.satisfied());
    }
    
    @Test
    public void shouldNotBeFulfilledOnStart() {
        WaitCondition condition = new WaitCondition(5000);
        condition.start();
        assertFalse(condition.satisfied());
    }
    
    @Test
    public void shouldBeFulfilledAfterTimePassed() throws InterruptedException {
        WaitCondition condition = new WaitCondition(10);
        
        condition.start();
        Thread.sleep(20);
        assertTrue(condition.satisfied());
    }

}
