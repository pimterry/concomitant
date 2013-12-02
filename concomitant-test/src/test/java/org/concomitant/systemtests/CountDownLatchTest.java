package org.concomitant.systemtests;

import static java.lang.Thread.State.TERMINATED;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.concomitant.Concomitant.announce;
import static org.concomitant.Concomitant.assertAllOtherThreadsAre;
import static org.concomitant.Concomitant.assertThisThreadIs;
import static org.concomitant.Concomitant.assertThreadsAre;
import static org.concomitant.Concomitant.blockedOrWaiting;
import static org.concomitant.Concomitant.event;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.concomitant.Concomitant;
import org.concomitant.ConcomitantRunner;
import org.concomitant.TestThread;
import org.concomitant.conditions.Event;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This is an example set of tests, checking the core concurrent functionality
 * of Java's built-in CountDownLatch component.
 */
@RunWith(ConcomitantRunner.class)
public class CountDownLatchTest {
    
    protected static final Event LATCH_DOWN_TO_0 = event("latch down");
    
    @Test
    public void latchAwaitShouldBlockUntilLatchIsAt0() throws Exception {
        final CountDownLatch latch = new CountDownLatch(2);
        
        final TestThread thread1 = new TestThread() {
            public void run() throws Throwable {
                assertThisThreadIs(Thread.State.WAITING).until(LATCH_DOWN_TO_0).on(latch).await();
                assertEquals(0, latch.getCount());
            }
        };
        
        final TestThread thread2 = new TestThread() {
            public void run() throws Throwable {
                assertAllOtherThreadsAre(Thread.State.WAITING).within(500, TimeUnit.MILLISECONDS).now();
                assertEquals(2, latch.getCount());
                
                latch.countDown();
                assertEquals(1, latch.getCount());
                assertAllOtherThreadsAre(blockedOrWaiting()).now();
                
                announce(LATCH_DOWN_TO_0).on(latch).countDown();
                assertEquals(0, latch.getCount());
            }
        };
        
        Concomitant.startTestThreads(thread1, thread2);
        assertThreadsAre(TERMINATED).forThreads(thread1, thread2).within(1000, MILLISECONDS).now();
    }
    
}
