package org.housered.concomitant;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;

import org.housered.concomitant.events.EventManager;

/**
 * A test context defines the state and fundamental behaviour Concomitant must use to
 * manage a single test execution. This consists of:
 *
 * <ul>
 * <li>The events manager itself, which tracks and controls what events have occurred so far in the test</li>
 * <li>The set of test threads involved in the test</li>
 * </ul>
 *
 * This is the core of the test state, which is then manipulated and examined by the various API methods and test rules
 */
public class TestContext {

    // TODO: Find a nicer way of doing this than with this messy singleton.
    private static TestContext currentContext = new TestContext();
    
    protected static TestContext context() {
        return currentContext;
    }
    
    private EventManager eventManager;
    private List<TestThread> testThreads;
    
    private TestContext() {
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