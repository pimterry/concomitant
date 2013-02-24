package org.housered.concomitant.monitoring;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.housered.concomitant.TestThread;
import org.housered.concomitant.announcements.Event;
import org.housered.concomitant.announcements.Tick;

public class ThreadMonitor {

    private Matcher<TestThread> matcher;
    private List<TestThread> threads;

    public ThreadMonitor(Matcher<TestThread> matcher, TestThread... threads) {
        this.matcher = matcher;
        this.threads = Arrays.asList(threads);
    }

    public ThreadMonitor until(Tick tick) {
        return this;
    }

    public ThreadMonitor until(Event latchFinished) {
        return this;
    }

    /**
     * Spins, checking for the given condition once per millisecond until it's true,
     * or the given period passes. 
     * 
     * It's not possible to get definitively notified on state changes, so very brief 
     * states matching the given thread condition could be missed if they last less than
     * one millisecond.
     */
    public void within(int duration, TimeUnit timeUnit) throws InterruptedException {
        long millisDuration = TimeUnit.MILLISECONDS.convert(duration, timeUnit);
        
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= millisDuration) {
            if (everyItem(matcher).matches(threads)) {
                return;
            }
            Thread.sleep(1);
        }
        assertThat(threads, everyItem(matcher));
    }

    /**
     * This should be called with the object on which the blocking call will be made, and should
     * be immediately followed with the actual blocking call.
     * 
     */
    public <T> T on(T thingToCall) {
        // Return a spy on the real object, which mocks everything to doAnswer with an answer that
        // starts a monitoring thread, calls the real method, and watches for it to return.
        
        // Monitoring thread watches for the liveness conditions, and ensures the real thread
        // is awake by that point
        
        // End of answer watches for the method to return, in case it doesn't block at all, and
        // confirms that the monitoring thread is happy everything has finished.
        
        // For now, probably just use Mockito directly, eventually maybe do the spying ourself, to remove
        // the dependency?
        return thingToCall;
    }

    protected List<TestThread> getMonitoredThreads() {
        return threads;
    }

}
