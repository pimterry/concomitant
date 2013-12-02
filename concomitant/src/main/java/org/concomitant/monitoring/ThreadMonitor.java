package org.concomitant.monitoring;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.concomitant.ConcomitantMisuseError;
import org.concomitant.TestThread;
import org.concomitant.conditions.Condition;
import org.concomitant.conditions.WaitCondition;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ThreadMonitor {

    private final Matcher<TestThread> matcher;
    private final List<TestThread> threads;
    
    private Condition untilCondition;
    private WaitCondition withinCondition = new WaitCondition(100);

    public ThreadMonitor(Matcher<TestThread> matcher, TestThread... threads) {
        this.matcher = matcher;
        this.threads = Arrays.asList(threads);
    }

    public ThreadMonitor until(Condition condition) {
        if (condition != null) {
            throw new ConcomitantMisuseError(
                    "You can't set multiple temporal conditions for a thread assertion "
                            + "(i.e. assert...until(x).until(y)) is not allowed");
        }

        this.untilCondition = condition;
        return this;
    }

    public ThreadMonitor within(int duration, TimeUnit timeUnit) throws InterruptedException {
        long millisDuration = TimeUnit.MILLISECONDS.convert(duration, timeUnit);
        withinCondition = new WaitCondition(millisDuration);
        return this;
    }
    
    public void now() throws InterruptedException {
        assertConditionFulfilledOnTime();
        assertConditionRemainsFulfilledLongEnough();
    }
    
    private void assertConditionFulfilledOnTime() throws InterruptedException {
        if (withinCondition != null) {
            withinCondition.start();
            while (!withinCondition.satisfied()) {
                if (everyItem(matcher).matches(threads)) {
                    return;
                }
                Thread.sleep(1);
            }
        }
        
        assertThat(threads, everyItem(matcher));
    }

    private void assertConditionRemainsFulfilledLongEnough() throws InterruptedException {
        assertThat(threads, everyItem(matcher));
        
        if (untilCondition == null) return;
        
        while (!untilCondition.satisfied()) {
            assertThat(threads, everyItem(matcher));
            Thread.sleep(1);
        }
    }

    /**
     * This should be called with the object on which the blocking call will be made, and should
     * be immediately followed with the actual blocking call.
     */
    @SuppressWarnings("unchecked")
    public <T> T on(T thingToCall) {        
        return (T) mock((Class<?>)thingToCall.getClass(), withSettings().defaultAnswer(new Answer<Object>() {
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                Future<Void> assertFuture = Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
                    public Void call() throws Exception {
                        ThreadMonitor.this.now();
                        return null;
                    }
                });
                
                Object result = invocation.callRealMethod();
                assertFuture.get(); // TODO What if the assertFuture is monitoring this thread?
                return result;
            }
        }).spiedInstance(thingToCall));
    }

    protected List<TestThread> getMonitoredThreads() {
        return threads;
    }

}
