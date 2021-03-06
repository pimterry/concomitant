package org.concomitant.rules;

import org.concomitant.TestContext;
import org.concomitant.TestThread;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class EnsureTestThreadsAllSucceed implements TestRule {

    private TestContext testContext;

    public EnsureTestThreadsAllSucceed(TestContext testContext) {
        this.testContext = testContext;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            public void evaluate() throws Throwable {
                base.evaluate();
                rethrowExceptionsFromTestThreads();
            }
        };
    }

    public void rethrowExceptionsFromTestThreads() throws Throwable {
        for (TestThread thread : testContext.testThreads()) {
            if (thread.threwException()) {
                throw thread.getThrown();
            }
        }
    }

}
