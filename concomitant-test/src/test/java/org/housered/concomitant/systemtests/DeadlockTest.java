package org.housered.concomitant.systemtests;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.housered.concomitant.Concomitant.announce;
import static org.housered.concomitant.Concomitant.assertThreadsAre;
import static org.housered.concomitant.Concomitant.blockedOrWaiting;
import static org.housered.concomitant.Concomitant.event;
import static org.housered.concomitant.Concomitant.waitFor;
import static org.junit.Assert.fail;

import org.housered.concomitant.Concomitant;
import org.housered.concomitant.ConcomitantRunner;
import org.housered.concomitant.TestThread;
import org.housered.concomitant.conditions.Event;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test deterministically sets up a deadlock, and passes the test by asserting on the deadlock's
 * existence
 *
 * The deadlock is the classic example, with two resources taken in opposite orders by two threads.
 * This is made deterministic by having each thread wait using the event API to ensure they only
 * grab their second resource once it's definitely already locked.
 *
 * The test then asserts that the threads are both locked as expected, kills the threads, and ends the test
 */
@RunWith(ConcomitantRunner.class)
public class DeadlockTest {

    protected static final Event LOCK_1_HELD = event("LOCK 1");
    protected static final Event LOCK_2_HELD = event("LOCK 2");

    @Test
    @Ignore // Doesn't work yet because there's no event management yet
    public void shouldBeAbleToDetectDeadlocks() throws InterruptedException {
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        
        final TestThread thread1 = new TestThread() {
            public void run() throws Exception {
                synchronized (lock1) {
                    announce(LOCK_1_HELD).now();
                    waitFor(LOCK_2_HELD);
                    
                    synchronized (lock2) {
                        fail("Both locks obtained by Thread 1");
                    }
                }
            }
        };
        
        final TestThread thread2 = new TestThread() {
            public void run() throws Exception {
                synchronized (lock2) {
                    announce(LOCK_2_HELD).now();
                    waitFor(LOCK_1_HELD);
                    
                    synchronized (lock1) {
                        fail("Both locks obtained by Thread 2");
                    }
                }
            }
        };
        
        Concomitant.startTestThreads(thread1, thread2);
        assertThreadsAre(blockedOrWaiting()).forThreads(thread1, thread2).within(100, MILLISECONDS);
    }
    
}
