package org.housered.concomitant.systemtests;

import static java.lang.Thread.State.BLOCKED;
import static java.lang.Thread.State.TERMINATED;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.housered.concomitant.Concomitant.*;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.housered.concomitant.Concomitant;
import org.housered.concomitant.ConcomitantRunner;
import org.housered.concomitant.TestThread;
import org.housered.concomitant.announcements.Event;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ConcomitantRunner.class)
public class CountDownLatchTest {
    
    protected static final Event LATCH_FINISHED = event("latch down");
    
    @Test
    public void testBasicCountDown() throws Exception {
        final CountDownLatch latch = new CountDownLatch(2);
        
        final TestThread thread1 = new TestThread() {
            public void run() throws Throwable {
                assertThisThreadIs(BLOCKED).until(LATCH_FINISHED).on(latch).await();
                assertEquals(0, latch.getCount());
            }
        };
        
        final TestThread thread2 = new TestThread() {
            public void run() throws Throwable {
                assertAllOtherThreadsAre(Thread.State.WAITING).within(500, TimeUnit.MILLISECONDS);
                assertEquals(2, latch.getCount());
                
                latch.countDown();
                assertEquals(1, latch.getCount());
                assertAllOtherThreadsAre(blockedOrWaiting());
                
                announce(LATCH_FINISHED).on(latch).countDown();
                assertEquals(0, latch.getCount());
            }
        };
        
        Concomitant.startTestThreads(thread1, thread2);
        assertThreadsAre(TERMINATED).forThreads(thread1, thread2).within(100, MILLISECONDS);
    }
    
}
