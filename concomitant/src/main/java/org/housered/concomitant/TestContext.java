package org.housered.concomitant;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;

import org.housered.concomitant.events.EventManager;

public class TestContext {

    private static TestContext currentContext = new TestContext();
    
    protected static TestContext context() {
        return currentContext;
    }
    
    private EventManager eventManager;
    private List<TestThread> testThreads;
    
    public TestContext() {
        reset();
    }
    
    public EventManager eventManager() {
        return this.eventManager;
    }
    
    public List<TestThread> testThreads() {
        return this.testThreads;
    }
    
    public void reset() {
        this.eventManager = new EventManager();
        this.testThreads = synchronizedList(new ArrayList<TestThread>());
    }
    
}