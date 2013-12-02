package org.concomitant.monitoring;

import org.hamcrest.Matcher;
import org.concomitant.TestThread;
import org.concomitant.ThreadWrappingTestThread;

public class ThreadSelector {

    private Matcher<TestThread> matcher;
    
    public ThreadSelector(Matcher<TestThread> matcher) {
        this.matcher = matcher;
    }

    public ThreadMonitor forThreads(Thread... threads) {
        TestThread[] testThreads = new TestThread[threads.length];
        for (int ii = 0; ii < threads.length; ii++) {
            testThreads[ii] = new ThreadWrappingTestThread(threads[ii]);
        }

        return this.forThreads(testThreads);
    }
    
    public ThreadMonitor forThreads(TestThread... threads) {
        return new ThreadMonitor(matcher, threads);
    }

}
