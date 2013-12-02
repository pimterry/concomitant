package org.concomitant;


public abstract class TestThread {

    private volatile Thread thread;
    private volatile Throwable thrown;
    
    public TestThread() {
        TestContext.context().testThreads().add(this);
    }
    
    public abstract void run() throws Throwable;
    
    public void setThread(Thread thread) {
        if (this.thread != null) {
            throw new ConcomitantMisuseError("TestThreads should only be run once");
        }
        
        this.thread = thread;
    }
    
    public Thread getThread() {
        return this.thread;
    }
    
    public void setThrownException(Throwable t) {
        this.thrown = t;
    }
    
    public Throwable getThrown() {
        return this.thrown;
    }
    
    public boolean threwException() {
        return this.thrown != null;
    }
    
}
