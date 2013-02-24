package org.housered.concomitant.functionaltests;

import static org.housered.concomitant.FunctionalTestUtils.runConcomitantTestClass;
import static org.junit.Assert.assertTrue;

import org.housered.concomitant.ConcomitantRunner;
import org.housered.concomitant.TestThread;
import org.junit.Test;
import org.junit.runner.RunWith;

public class ThreadManagementTests {
    
    @RunWith(ConcomitantRunner.class)
    public static class TestClassWithImplicitlyStartedThread {
        
        private static boolean threadWasRun = false;
     
        @Test
        public void testWhichDetectsWhetherTheContainedThreadWasRun() throws InterruptedException {
            new TestThread() {
                public void run() throws Throwable {
                    threadWasRun = true;
                }
            };
        }
    }
    
    @Test
    public void testThreadsDefinedInTestsShouldRunAutomatically() throws Exception {
        TestClassWithImplicitlyStartedThread.threadWasRun = false;
        
        runConcomitantTestClass(TestClassWithImplicitlyStartedThread.class);
        
        Thread.sleep(100);
        assertTrue(TestClassWithImplicitlyStartedThread.threadWasRun);
    }
    
    @RunWith(ConcomitantRunner.class)
    public static class TestClassWithImplicitStartedThreadThatNeedsTime {
        
        private static boolean threadFinished = false;
     
        @Test
        public void testWhichDetectsWhetherTheContainedThreadFinished() throws InterruptedException {
            new TestThread() {
                public void run() throws Throwable {
                    Thread.sleep(100);
                    threadFinished = true;
                }
            };
        }
    }
    
    @Test
    public void shouldWaitForAutomaticallyStartedThreadsToTerminate() throws Exception {
        TestClassWithImplicitStartedThreadThatNeedsTime.threadFinished = false;
        
        runConcomitantTestClass(TestClassWithImplicitStartedThreadThatNeedsTime.class);
        
        assertTrue(TestClassWithImplicitStartedThreadThatNeedsTime.threadFinished);
    }    
    
    
}
