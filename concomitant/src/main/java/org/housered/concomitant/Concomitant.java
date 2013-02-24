package org.housered.concomitant;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.housered.concomitant.monitoring.TestThreadRunner.currentThread;

import java.util.List;

import org.hamcrest.Matcher;
import org.housered.concomitant.announcements.Announcer;
import org.housered.concomitant.announcements.Event;
import org.housered.concomitant.announcements.EventAnnouncer;
import org.housered.concomitant.announcements.Tick;
import org.housered.concomitant.announcements.TickAnnouncer;
import org.housered.concomitant.monitoring.TestThreadRunner;
import org.housered.concomitant.monitoring.ThreadMonitor;
import org.housered.concomitant.monitoring.ThreadSelector;
import org.housered.concomitant.rules.TestThreadMonitorRule;

public class Concomitant {
    
    public static Matcher<TestThread> blockedOrWaiting() {
        return anyOf(new ThreadStateMatcher(Thread.State.BLOCKED), 
                     new ThreadStateMatcher(Thread.State.WAITING));
    }

    public static ThreadMonitor assertThisThreadIs(Thread.State threadState) {
        return assertThisThreadIs(new ThreadStateMatcher(threadState));
    }
    
    public static ThreadMonitor assertThisThreadIs(Matcher<TestThread> threadMatcher) {
        return new ThreadMonitor(threadMatcher, currentThread());
    }
    
    public static ThreadMonitor assertAllOtherThreadsAre(Thread.State threadState) {
        return assertAllOtherThreadsAre(new ThreadStateMatcher(threadState));
    }
    
    public static ThreadMonitor assertAllOtherThreadsAre(Matcher<TestThread> threadMatcher) {
        List<TestThread> threads = TestThreadMonitorRule.getTestThreads();
        threads.remove(currentThread());
        return new ThreadMonitor(threadMatcher, threads.toArray(new TestThread[0]));
    }
    
    public static ThreadSelector assertThreadsAre(Thread.State state) {
        return assertThreadsAre(new ThreadStateMatcher(state));
    }
    
    public static ThreadSelector assertThreadsAre(Matcher<TestThread> threadMatcher) {
        return new ThreadSelector(threadMatcher);
    }    
    
    /**
     * Sets the current tick to the given Tick value. This cannot decrease the tick, doing
     * so will throw an IllegalArgumentException.
     */
    public static Announcer announce(Tick tick) {
        return new TickAnnouncer(tick);
    }
    
    public static Announcer announce(Event event) {
        return new EventAnnouncer(event);
    }
    
    public static void waitFor(Event event) {
    }
    
    public static void waitFor(Tick event) {
    }    
    
    public static Tick tick(int tickNum) {
        return new Tick(tickNum);
    }
    
    public static Event event(String eventName) {
        return null;
    }    

    public static void startTestThreads() {
    }
    
    public static void startTestThreads(TestThread... testThreads) {
        for (TestThread thread : testThreads) {
            new TestThreadRunner(thread).start();
        }
    }
    
}
