package org.concomitant;

public class ThreadWrappingTestThread extends TestThread {

    private Thread wrappedThread;

    public ThreadWrappingTestThread(Thread thread) {
        this.wrappedThread = thread;
    }

    @Override
    public void run() throws Throwable {
        this.wrappedThread.run();
    }
    
}
