package org.housered.concomitant.rules;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;

import org.housered.concomitant.TestThread;
import org.housered.concomitant.TestThreadListener;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestThreadMonitorRule implements TestRule {

    private final static List<TestThread> threads = synchronizedList(new ArrayList<TestThread>());
    
    public static List<TestThread> getTestThreads() {
        return new ArrayList<TestThread>(threads);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new TestThreadMonitorStatement(base);
    }
    
    private static class TestThreadMonitorStatement extends Statement implements TestThreadListener {
        
        private Statement base;

        public TestThreadMonitorStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            TestThread.setTestThreadListener(this);
            try {
                base.evaluate();
            } finally {
                threads.clear();
            }
        }

        @Override
        public void announceCreation(TestThread thread) {
            threads.add(thread);
        }
    }

}
