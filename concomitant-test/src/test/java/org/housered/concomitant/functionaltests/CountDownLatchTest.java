package org.housered.concomitant.functionaltests;

import static org.housered.concomitant.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.housered.concomitant.TestThread;
import org.junit.Test;

public class CountDownLatchTest {
    
    CountDownLatch latch;
    
    @Test
    public void testBasicCountDown() throws Exception {
        latch = new CountDownLatch(2);
        
        new TestThread() {
            public void run() throws Exception {
                verifyBlocked().until(tick(1)).on(latch).await();
                assertEquals(0, latch.getCount());
            }
        };
        
        new TestThread() {
            public void run() throws Exception {
                verifyBlocked().within(100, TimeUnit.MILLISECONDS);
                
                assertEquals(2, latch.getCount());
                latch.countDown();
                
                assertEquals(1, latch.getCount());
                assertAllBlocked();
                
                announce(tick(1));
            }
        };
    }
    
}
