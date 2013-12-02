package org.concomitant.functionaltests;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.concomitant.Concomitant.assertThreadsAre;
import static org.concomitant.FunctionalTestUtils.runConcomitantTestClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.lang.Thread.State;

import org.concomitant.Concomitant;
import org.concomitant.ConcomitantRunner;
import org.concomitant.TestThread;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

public class ThreadFailureTests {

    @Ignore // This is run and examined by the below tests, not standalone.
    @RunWith(ConcomitantRunner.class)
    public static class TestClassWithFailingThread {
        
        private static Throwable expectedException = new IllegalStateException();
     
        @Test
        public void testWhichShouldFailDueToThreadThrowingException() throws InterruptedException {
            TestThread thread = new TestThread() {
                public void run() throws Throwable {
                    throw expectedException;
                }
            };
            
            Concomitant.startTestThreads(thread);
            assertThreadsAre(State.TERMINATED).forThreads(thread).within(100, MILLISECONDS);
        }
    }
    
    @Test
    public void testsShouldAutomaticallyFailIfThreadsFail() throws InitializationError {
        Result testResult = runConcomitantTestClass(TestClassWithFailingThread.class);
        
        assertEquals(1, testResult.getRunCount());
        assertEquals(1, testResult.getFailureCount());
        assertSame(TestClassWithFailingThread.expectedException, 
                testResult.getFailures().get(0).getException());
    }
}
