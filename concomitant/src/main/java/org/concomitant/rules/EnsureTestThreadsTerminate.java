package org.concomitant.rules;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import org.concomitant.TestContext;
import org.concomitant.TestThread;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Work in progress; this doesn't yet work correctly at all.
 */
public class EnsureTestThreadsTerminate implements TestRule {
    
    private TestContext testContext;

    public EnsureTestThreadsTerminate(TestContext testContext) {
        this.testContext = testContext;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new RunTestInOtherThreadAndMonitorDeadlockStatement(base);
    }
    
    private static boolean isBlocked(Thread thread) {
        State threadState = thread.getState();
        return (threadState == State.BLOCKED || threadState == State.WAITING);
    }
    
    private class RunTestInOtherThreadAndMonitorDeadlockStatement extends Statement {

        private static final long DEADLOCK_POLLING_INTERVAL = 50;
        
        private Statement base;
        private Throwable thrown = null;

        private Thread actualThread;

        public RunTestInOtherThreadAndMonitorDeadlockStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            actualThread = new Thread(new ActualTestRunnable(base));
            actualThread.start();
            
            while (areAnyThreadsRunning()) {
                joinRunningThreads(DEADLOCK_POLLING_INTERVAL);
            }
            
            checkAllThreadsAreTerminated();

            if (thrown != null) {
                throw thrown;
            }
        }

        private void checkAllThreadsAreTerminated() throws Throwable {
            if (!areAllThreadsTerminated()) {
                for (TestThread testThread : testContext.testThreads()) {
                    if (testThread.threwException()) {
                        throw new IllegalStateException("Deadlock! Probably caused by exception thrown from test thread: ", testThread.getThrown());
                    }
                }

                for (Thread thread : getLiveThreads()) {
                    printThreadStackTrace(thread);
                }
                throw new IllegalStateException("Deadlock!");
            }
        }

        private boolean areAllThreadsTerminated() {
            return getLiveThreads().isEmpty();
        }
        
        private void printThreadStackTrace(Thread thread) {
            for (StackTraceElement element : thread.getStackTrace()) {
                System.out.println(element.toString());
            }
        }

        private List<Thread> getLiveThreads() {
            List<Thread> threads = new ArrayList<Thread>();
            
            if (actualThread.isAlive()) {
                threads.add(actualThread);
            }
            
            for (TestThread testThread : testContext.testThreads()) {
                Thread realThread = testThread.getThread();
                
                // Real thread will be null when the TestThread has been created
                // but nothing has yet tried to start it.
                if (realThread != null && realThread.isAlive()) {
                    threads.add(realThread);
                }
            }
            
            return threads;
        }
        
        private boolean areAnyThreadsRunning() {
            for (Thread thread : getLiveThreads()) {
                if (!isBlocked(thread)) {
                    return true;
                }
            }
            return false;
        }
        
        private void joinRunningThreads(long timeout) throws InterruptedException {
            List<Thread> threads = getLiveThreads();
            if (!threads.isEmpty()) {
                threads.get(0).join(timeout);
            }
        }

        // TODO: Refactor this into just another TestThread + runner, so we can catch exceptions via EnsureTestThreadsAllSucceed instead
        private class ActualTestRunnable implements Runnable {

            private Statement base;

            public ActualTestRunnable(Statement base) {
                this.base = base;
            }

            @Override
            public void run() {
                try {
                    base.evaluate();
                } catch (Throwable e) {
                    RunTestInOtherThreadAndMonitorDeadlockStatement.this.thrown = e;
                }
            }
            
        }
        
    }

}
