package org.housered.concomitant.rules;

import org.housered.concomitant.TestThread;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class EnsureTestThreadsAllSucceed implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new TestThreadsMustSuccessStatement(base);
    }
    
    private class TestThreadsMustSuccessStatement extends Statement {

        private Statement base;

        public TestThreadsMustSuccessStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            base.evaluate();
            rethrowExceptionsFromTestThreads();
        }
        
    }

    public void rethrowExceptionsFromTestThreads() throws Throwable {
        for (TestThread thread : TestThreadMonitorRule.getTestThreads()) {
            if (thread.threwException()) {
                throw thread.getThrown();
            }
        }
    }

}