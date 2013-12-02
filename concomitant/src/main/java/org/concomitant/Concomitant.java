package org.concomitant;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.concomitant.TestContext.context;
import static org.concomitant.monitoring.TestThreadRunner.currentThread;

import java.util.List;

import org.hamcrest.Matcher;
import org.concomitant.conditions.Event;
import org.concomitant.events.EventAnnouncer;
import org.concomitant.monitoring.TestThreadRunner;
import org.concomitant.monitoring.ThreadMonitor;
import org.concomitant.monitoring.ThreadSelector;

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
        List<TestThread> threads = context().testThreads();
        threads.remove(currentThread());
        return new ThreadMonitor(threadMatcher, threads.toArray(new TestThread[0]));
    }
    
    public static ThreadSelector assertThreadsAre(Thread.State state) {
        return assertThreadsAre(new ThreadStateMatcher(state));
    }
    
    public static ThreadSelector assertThreadsAre(Matcher<TestThread> threadMatcher) {
        return new ThreadSelector(threadMatcher);
    }
    
    public static EventAnnouncer announce(Event event) {
        return new EventAnnouncer(event, context().eventManager());
    }
    
    public static void waitFor(Event event) {
    }
    
    public static Event event(String eventName) {
        return null;
    }
    
    public static void startTestThreads(TestThread... testThreads) {
        for (TestThread thread : testThreads) {
            new TestThreadRunner(thread).start();
        }
    }
    
}
