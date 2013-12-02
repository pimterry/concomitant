package org.housered.concomitant.monitoring;

import org.housered.concomitant.TestThread;

/**
 * Wraps a test thread with a runnable that captures issues (exceptions + misuse) for
 * test reporting, and provides a mechanism to get the current TestThread.
 */
public class TestThreadRunner implements Runnable {

    private static ThreadLocal<TestThread> currentThread = new ThreadLocal<TestThread>();

    private Thread thread;
    private TestThread testThread;

    public TestThreadRunner(TestThread thread) {
        this.testThread = thread;
        this.thread = new Thread(this);
        testThread.setThread(this.thread);
    }
    
    public void start() {
        this.thread.start();
    }
    
    public Thread getThread() {
        return thread;
    }
    
    public static TestThread currentThread() {
        return currentThread.get();
    }

    @Override
    public void run() {
        try {
            currentThread.set(testThread);
            testThread.run();
            // checkConcomitantUsage();
        } catch (Throwable t) {
            testThread.setThrownException(t);
        }
    }
    
}
