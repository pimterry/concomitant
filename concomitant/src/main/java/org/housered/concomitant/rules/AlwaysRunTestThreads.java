package org.housered.concomitant.rules;

import org.housered.concomitant.Concomitant;
import org.housered.concomitant.TestThread;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class AlwaysRunTestThreads implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new TestThreadRunnerStatement(base);
    }
    
    private static class TestThreadRunnerStatement extends Statement {
        
        private Statement base;

        public TestThreadRunnerStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            base.evaluate();
            
            for (TestThread testThread : TestThreadMonitorRule.getTestThreads()) {
                if (testThread.getThread() == null) {
                    Concomitant.startTestThreads(testThread);
                }
            }
        }
    }

}
